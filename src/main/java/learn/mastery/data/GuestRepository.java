package learn.mastery.data;

import learn.mastery.models.Guest;

public interface GuestRepository {
    public Guest getGuestById(int guestId);
}
