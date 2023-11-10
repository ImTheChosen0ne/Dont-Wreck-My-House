package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> findByHost(Host host);
    public Reservation add(Host host, Reservation reservation) throws DataException;
    public boolean update(Host host, Reservation reservation) throws DataException;
    public boolean delete(Host host, Reservation reservation) throws DataException;

}
