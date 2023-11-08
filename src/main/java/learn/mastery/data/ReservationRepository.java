package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> findByHost(Host host);
    public Reservation add(Reservation reservation);
    public boolean update(Reservation reservation);
    public boolean deleteById(int reservationId);

}
