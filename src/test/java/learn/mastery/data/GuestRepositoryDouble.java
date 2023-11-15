package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;

public class GuestRepositoryDouble implements GuestRepository{
    final Guest GUEST = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
    private ArrayList<Guest> guests = new ArrayList<>();
    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }

    @Override
    public Guest getGuestById(int guestId) {
        return guests.stream()
                .filter(i -> i.getId() == guestId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest getGuestByEmail(String guestEmail) {
        return guests.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(guestEmail))
                .findFirst()
                .orElse(null);
    }
}
