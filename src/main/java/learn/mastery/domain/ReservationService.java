package learn.mastery.domain;

import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> findByHost(Host host) {
        return null;
    }
    public Result add(Reservation reservation) {
        return null;
    }
    public Result update(Reservation reservation) {
        return null;
    }
    public Result delete(Reservation reservation) {
        return null;
    }
    private Result validate(Reservation reservation) {
        return null;
    }
}
