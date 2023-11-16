package learn.mastery.ui;

import learn.mastery.domain.GuestService;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
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

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void printReservations(Host host, List<Reservation> reservations, GuestService guestService) {
        printHeader(host.getLastName() + ": " + host.getCity() + ", " + host.getState());

        List<Reservation> matchingReservations = reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList());

        if (matchingReservations.size() == 0) {
            io.println("No current reservations.");
        } else {
            matchingReservations.forEach(reservation -> {
                Guest guest = guestService.getGuestById(reservation.getGuest().getId());
                String guestInfo = "ID: " + reservation.getId() +
                        ", " + reservation.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))  + " - " + reservation.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))  +
                        ", Guest: " + guest.getLastName() + ", " + guest.getFirstName() +
                        ", Email: " + guest.getEmail();
                io.println(guestInfo);
            });
        }
    }

    public Reservation chooseReservation(Host host, List<Reservation> reservations, Guest guest) {
        printHeader(host.getLastName() + ": " + host.getCity() + ", " + host.getState());

        if (reservations.size() == 0) {
            return null;
        }

        List<Reservation> matchingReservations = reservations.stream()
                .filter(reservation -> reservation.getGuest().getId() == guest.getId())
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList());

        if (matchingReservations.size() == 0) {
            return null;
        }

        matchingReservations.forEach(reservation -> {
            String guestInfo = "ID: " + reservation.getId() +
                    ", " + reservation.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))  + " - " + reservation.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))  +
                    ", Guest: " + guest.getLastName() + ", " + guest.getFirstName() +
                    ", Email: " + guest.getEmail();
            io.println(guestInfo);
        });

        int choice = io.readInt("Reservation ID: ");

        return matchingReservations.stream()
                .filter(reservation -> reservation.getId() == choice)
                .findFirst()
                .orElse(null);
    }

    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();

        LocalDate start = io.readLocalDate("Start (MM/dd/yyyy): ");
        LocalDate end = io.readLocalDate("End (MM/dd/yyyy): ");

        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setHost(host);
        reservation.setGuest(guest);

        BigDecimal total = calculateTotal(host, start, end).setScale(2, BigDecimal.ROUND_HALF_UP);
        reservation.setTotal(total);

        return reservation;
    }

    public Reservation update(Host host, Reservation reservation) {
        LocalDate start = io.readLocalDateUpdate("Start (" + reservation.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "): ", reservation.getStart());

        if (start != null) {
            reservation.setStart(start);
        }

        LocalDate end = io.readLocalDateUpdate("End (" + reservation.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "): ", reservation.getEnd());

        if (end != null) {
            reservation.setEnd(end);
        }

        BigDecimal total = calculateTotal(host, start, end).setScale(2, BigDecimal.ROUND_HALF_UP);
        reservation.setTotal(total);

        reservation.setHost(host);

        return reservation;
    }

    public void printHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public boolean confirmSummary(Reservation reservation) {
        printHeader("Summary");
        io.println("Start: " + reservation.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) );
        io.println("End: " + reservation.getEnd().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) );
        io.println("Total: $" + reservation.getTotal());
        boolean confirm = io.readBoolean("Is this okay? [y/n]: ");
        if (confirm) {
            return true;
        } else {
            return false;
        }
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        printHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
    public String getHostEmail() {
        return io.readRequiredString("Host Email: ");
    }

    public String getGuestEmail() {
        return io.readRequiredString("Guest Email: ");
    }

    public void displayException(Exception ex) {
        printHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    private BigDecimal calculateTotal(Host host, LocalDate start, LocalDate end) {
        BigDecimal total = BigDecimal.ZERO;
        LocalDate currentDate = start;

        while (!currentDate.isAfter(end.minusDays(1))) {
            BigDecimal dailyRate = isWeekendNight(currentDate) ? host.getWeekendRate() : host.getStandardRate();
            total = total.add(dailyRate);
            currentDate = currentDate.plusDays(1);
        }

        return total;
    }

    private boolean isWeekendNight(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}

