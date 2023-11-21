package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
    void shouldNotAddWithExistingDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,12,5));
        reservation.setEnd(LocalDate.of(2023,12,12));
        reservation.setGuest(GUEST);


        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Existing Reservation", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddWithInvalidDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,11,1));
        reservation.setEnd(LocalDate.of(2023,11,5));
        reservation.setGuest(GUEST);


        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Start date must be in the future.", result.getErrorMessages().get(0));

        Reservation reservation2 = new Reservation();
        reservation2.setHost(HOST);
        reservation2.setStart(LocalDate.of(2023,11,6));
        reservation2.setEnd(LocalDate.of(2023,11,5));
        reservation2.setGuest(GUEST);


        Result<Reservation> result2 = service.add(HOST, reservation2);
        assertFalse(result2.isSuccess());
        assertNull(result2.getPayload());
        assertEquals("Start date must be before end date.", result2.getErrorMessages().get(0));
    }
    @Test
    void shouldNotAddWithoutHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(null);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Host is required.", result.getErrorMessages().get(0));
    }
    @Test
    void shouldNotAddWithoutGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(null);

        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Guest is required.", result.getErrorMessages().get(0));
    }
    @Test
    void shouldNotAddWithoutStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(null);
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Start Date is required.", result.getErrorMessages().get(0));
    }
    @Test
    void shouldAddWithoutEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(null);
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.add(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("End Date is required.", result.getErrorMessages().get(0));
    }
    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = service.findByHost(HOST).get(0);

        reservation.setStart(LocalDate.of(2023,12,2));
        reservation.setEnd(LocalDate.of(2023,12,7));

        Result<Reservation> result = service.update(HOST, reservation);

        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(LocalDate.of(2023,12,2), result.getPayload().getStart());
    }

    @Test
    void shouldNotUpdateWithoutHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(null);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.update(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Host is required.", result.getErrorMessages().get(0));
    }
    @Test
    void shouldNotUpdateWithoutGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2023,12,15));
        reservation.setEnd(LocalDate.of(2023,12,20));
        reservation.setGuest(null);

        Result<Reservation> result = service.update(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Guest is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotUpdatePastReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(HOST);
        reservation.setStart(LocalDate.of(2021,12,15));
        reservation.setEnd(LocalDate.of(2021,12,20));
        reservation.setGuest(GUEST);

        Result<Reservation> result = service.update(HOST, reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("Cannot update a past reservation.", result.getErrorMessages().get(1));
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = service.findByHost(HOST).get(0);
        Result<Reservation> result = service.delete(HOST, reservation);
        assertTrue(result.isSuccess());
    }
}

