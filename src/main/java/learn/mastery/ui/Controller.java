package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {
    private View view;
    private ReservationService reservationService;
    private HostService hostService;
    private GuestService guestService;

    public Controller(View view, ReservationService reservationService, HostService hostService, GuestService guestService) {
        this.view = view;
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
    }

    public void run() {
        view.printHeader("Don't Wreck My House");
        try {
            runMenu();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.printHeader("Goodbye.");
    }

    private void runMenu() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewByHost();
                    break;
                case MAKE_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }
    private void viewByHost() {
        view.printHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());

        Host host = getHostByEmail();

        List<Reservation> reservations = reservationService.findByHost(host);
        view.printReservations(host, reservations, guestService);
    }

    private void addReservation() throws DataException {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());

        Guest guest = getGuestByEmail();
        Host host = getHostByEmail();

        List<Reservation> reservations = reservationService.findByHost(host);
        view.printReservations(host, reservations, guestService);

        Reservation reservation = view.makeReservation(host, guest);

        Result<Reservation> result = reservationService.add(host, reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            boolean confirm = view.confirmSummary(reservation);
            if (!confirm) {
                runMenu();
            } else {
                String successMessage = String.format("Reservation %s created.", result.getPayload().getId());
                view.displayStatus(true, successMessage);
            }
        }
//        boolean confirm = view.confirmSummary(reservation);
//            if (!confirm) {
//                runMenu();
//            } else {
//                Result<Reservation> result = reservationService.add(host, reservation);
//                if (!result.isSuccess()) {
//                    view.displayStatus(false, result.getErrorMessages());
//                } else {
//                    String successMessage = String.format("Reservation %s created.", result.getPayload().getId());
//                    view.displayStatus(true, successMessage);
//                }
//            }
    }

    private void updateReservation() throws DataException {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());

        Guest guest = getGuestByEmail();
        Host host = getHostByEmail();

        List<Reservation> reservations = reservationService.findByHost(host);

        Reservation findReservations = view.chooseReservation(host, reservations, guest);

        if (findReservations != null) {
            view.printHeader("Editing Reservation " + findReservations.getId());
            Reservation reservation = view.update(host, findReservations);

            Result<Reservation> result = reservationService.update(host, reservation);
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
            } else {
                boolean confirm = view.confirmSummary(reservation);
                if (!confirm) {
                    runMenu();
                } else {
                    String successMessage = String.format("Reservation %s updated.", result.getPayload().getId());
                    view.displayStatus(true, successMessage);
                }
            }
//            boolean confirm = view.confirmSummary(reservation);
//            if (!confirm) {
//                runMenu();
//            } else {
//                Result<Reservation> result = reservationService.update(host, reservation);
//                if (!result.isSuccess()) {
//                    view.displayStatus(false, result.getErrorMessages());
//                } else {
//                    String successMessage = String.format("Reservation %s updated.", result.getPayload().getId());
//                    view.displayStatus(true, successMessage);
//                }
//            }
        } else {
            view.displayStatus(false, "Reservation does not exist.");
        }
    }

    private void deleteReservation() throws DataException {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());

        Guest guest = getGuestByEmail();
        Host host = getHostByEmail();

        List<Reservation> reservations = reservationService.findByHost(host);

        Reservation findReservations = view.chooseReservation(host, reservations, guest);

//        Result result = reservationService.delete(host, findReservations);

        if (findReservations != null) {
            Result result = reservationService.delete(host, findReservations);
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
            } else {
                view.displayStatus(true, "Reservation " + findReservations.getId() + " canceled.");
            }
        } else {
            view.displayStatus(false, "Reservation does not exist.");
        }
    }

    private Guest getGuestByEmail() {
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.getGuestByEmail(guestEmail);

        if (guest == null) {
            view.displayStatus(false, "Guest not found with the specified email.\nPlease re-enter a new guest email.");
            System.out.println();
            return getGuestByEmail();
        }

        return guest;
    }

    private Host getHostByEmail() {
        String hostEmail = view.getHostEmail();
        Host host = hostService.getHostByEmail(hostEmail);

        if (host == null) {
            view.displayStatus(false, "Host not found with the specified email.\nPlease re-enter a new host email.");
            System.out.println();
            return getHostByEmail();
        }

        return host;
    }
}
