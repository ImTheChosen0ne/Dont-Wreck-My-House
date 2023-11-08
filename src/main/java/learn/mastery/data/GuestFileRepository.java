package learn.mastery.data;

import learn.mastery.models.Guest;

public class GuestFileRepository implements GuestRepository{
    private String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Guest getGuestById(int guestId) {
        return null;
    }

    private Guest deserialize(String value) {
        return null;
    }
}
