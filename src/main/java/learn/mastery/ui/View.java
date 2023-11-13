package learn.mastery.ui;

import learn.mastery.domain.GuestService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {
    private final ConsoleIO io;

    private final GuestService guestRepository;

    public View(ConsoleIO io, GuestService guestRepository) {
        this.io = io;
        this.guestRepository = guestRepository;
    }

    public MainMenuOption selectMainMenuOption() {
        printHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void printReservations(Host host, List<Reservation> reservations) {
        printHeader(host.getLastName() + ": " + host.getCity() + ", " + host.getState());

        reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .forEach(reservation -> {
                    Guest guest = guestRepository.getGuestById(reservation.getGuest().getId());
                    String guestInfo = "ID: " + reservation.getId() +
                            ", " + reservation.getStart() + " - " + reservation.getEnd() +
                            ", Guest: " + guest.getLastName() + ", " + guest.getFirstName() +
                            ", Email: " + guest.getEmail();
                    System.out.println(guestInfo);
                });
    }

    public Reservation chooseReservation(Host host, List<Reservation> reservation) {
//        displayItems(items);
//
//        if (items.size() == 0) {
//            return null;
//        }
//
//        int itemId = io.readInt("Select an item id: ");
//        Item item = items.stream()
//                .filter(i -> i.getId() == itemId)
//                .findFirst()
//                .orElse(null);
//
//        if (item == null) {
//            displayStatus(false, String.format("No item with id %s found.", itemId));
//        }
//
//        return item;
        return null;
    }

    public Reservation makeReservation() {
//        Forager forager = new Forager();
//        forager.setFirstName(io.readRequiredString("Forager First Name: "));
//        forager.setLastName(io.readRequiredString("Forager Last Name: "));
//        forager.setState(io.readRequiredString("Forager State: "));
//        return forager;
        return null;
    }

    public Reservation update(Reservation reservation) {
//        printHeader("Update");
//
//        String from = io.readString("From (" + m.getFrom() + "): ");
//        // only update if it changed
//        if (from.trim().length() > 0) {
//            m.setFrom(from);
//        }
//
//        String content = io.readString("Content (" + m.getContent() + "): ");
//        if (content.trim().length() > 0) {
//            m.setContent(content);
//        }
//
//        String shareable = io.readString("Shareable (" + (m.isShareable() ? "y" : "n") + ") [y/n]: ");
//        if (shareable.trim().length() > 0) {
//            m.setShareable(shareable.equalsIgnoreCase("y"));
//        }
//        return m;
        return null;
    }

    public void printHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void printResult(Result result) {
        if (result.isSuccess()) {
            printHeader("Success");
        } else {
            printHeader("Error");
            for (String errorMessage : result.getErrorMessages()) {
                System.out.println(errorMessage);
            }
        }
    }

    public boolean confirmSummary(Reservation reservation) {
        return false;
    }

    public String getHostEmail() {
        return io.readRequiredString("Host Email: ");
    }


}

