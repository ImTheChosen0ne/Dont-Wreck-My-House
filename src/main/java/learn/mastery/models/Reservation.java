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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

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
