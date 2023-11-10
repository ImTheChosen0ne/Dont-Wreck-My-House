package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

public class GuestService {
    private GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest getGuestById(int guestId) {
        return repository.getGuestById(guestId);
    }
}
