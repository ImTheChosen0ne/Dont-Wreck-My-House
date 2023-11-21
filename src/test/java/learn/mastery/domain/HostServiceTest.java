package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldGetHostByEmail() {
        Host host = service.getHostByEmail("kwigfieldiy@php.net");
        assertNotNull(host);
        assertEquals("Wigfield", host.getLastName());
        assertEquals("9d469342-ad0b-4f5a-8d28-e81e690ba29a", host.getId());
        assertEquals("88875 Miller Parkway", host.getAddress());
    }
}