package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldGetGuestById() {
        Guest guest = service.getGuestById(1);
        assertNotNull(guest);
        assertEquals("Lomas", guest.getLastName());
        assertEquals(1, guest.getId());
        assertEquals("(702) 7768761", guest.getPhone());
    }

    @Test
    void shouldGetGuestByEmail() {
        Guest guest = service.getGuestByEmail("slomas0@mediafire.com");
        assertNotNull(guest);
        assertEquals("Lomas", guest.getLastName());
        assertEquals(1, guest.getId());
        assertEquals("(702) 7768761", guest.getPhone());
    }
}