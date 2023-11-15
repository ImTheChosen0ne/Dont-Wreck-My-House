package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class ReservationServiceTest {
    final Host HOST = new Host("9d469342-ad0b-4f5a-8d28-e81e690ba29a","Wigfield","kwigfieldiy@php.net","(305) 8769397","88875 Miller Parkway","Miami","FL",33185,new BigDecimal(435),new BigDecimal(543.75));
    final Guest GUEST = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.add(HOST, reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = service.findByHost(HOST).get(0);

        reservation.setStart(LocalDate.of(2024,1,15));
        reservation.setEnd(LocalDate.of(2024,1,20));

        Result<Reservation> result = service.update(HOST, reservation);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(LocalDate.of(2024,1,15), result.getPayload().getStart());
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = service.findByHost(HOST).get(0);
        Result<Reservation> result = service.delete(HOST, reservation);
        assertTrue(result.isSuccess());
    }
}

