package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{
    @Override
    public List<Reservation> findByHost(Host host) {
        return null;
    }

    @Override
    public Reservation add(Host host, Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Host host, Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean delete(Host host, Reservation reservation) throws DataException {
        return false;
    }
}
