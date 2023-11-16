package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {
    final static Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");

    final static Host host = new Host("9d469342-ad0b-4f5a-8d28-e81e690ba29a","Wigfield","kwigfieldiy@php.net","(305) 8769397","88875 Miller Parkway","Miami","FL",33185,new BigDecimal(435),new BigDecimal(543.75));

    public final static Reservation RESERVATION = new Reservation(1, LocalDate.of(2023,12,1), LocalDate.of(2023,12,7), host, guest, new BigDecimal(1000));

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        reservations.add(RESERVATION);
    }
    @Override
    public List<Reservation> findByHost(Host host) {
        return reservations.stream()
                .filter(i -> i.getHost().getId().equals(host.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Host host, Reservation reservation) {
        reservation.setId(2);
        reservation.setHost(host);
        reservation.setStart(LocalDate.now());
        reservation.setEnd(LocalDate.now().plusDays(7));
        reservation.setGuest(guest);
        reservation.setTotal(new BigDecimal(1000));

        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Host host, Reservation reservation) {
        List<Reservation> reservations = findByHost(host);
        for (Reservation r : reservations) {
            if (r.getId() == reservation.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Host host, Reservation reservation) {
        List<Reservation> reservations = findByHost(host);
        for (Reservation r : reservations) {
            if (r.getId() == reservation.getId()) {
                reservations.remove(r);
                return true;
            }
        }
        return false;
    }
}
