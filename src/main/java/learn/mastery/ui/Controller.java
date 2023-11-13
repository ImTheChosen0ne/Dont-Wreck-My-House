package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
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
            runMenu();
        view.printHeader("Goodbye.");
    }

    private void runMenu() {
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
        view.printHeader("View Reservations for Host");
        String hostEmail = view.getHostEmail();
        Host host = hostService.getHostByEmail(hostEmail);
        List<Reservation> reservations = reservationService.findByHost(host);
        view.printReservations(host, reservations);
    }

    private void addReservation() {
//        view.displayHeader(MainMenuOption.ADD_FORAGE.getMessage());
//        Forager forager = getForager();
//        if (forager == null) {
//            return;
//        }
//        Item item = getItem();
//        if (item == null) {
//            return;
//        }
//        Forage forage = view.makeForage(forager, item);
//        Result<Forage> result = forageService.add(forage);
//        if (!result.isSuccess()) {
//            view.displayStatus(false, result.getErrorMessages());
//        } else {
//            String successMessage = String.format("Forage %s created.", result.getPayload().getId());
//            view.displayStatus(true, successMessage);
//        }
    }
    private void updateReservation() {

    }
    private void deleteReservation() {

    }
}
