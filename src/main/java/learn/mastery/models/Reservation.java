package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Host host;
    private Guest guest;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal total;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
