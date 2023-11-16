package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/reservations-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    final Host host = new Host("9d469342-ad0b-4f5a-8d28-e81e690ba29a","Wigfield","kwigfieldiy@php.net","(305) 8769397","88875 Miller Parkway","Miami","FL",33185,new BigDecimal(435),new BigDecimal(543.75));

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);


    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart(LocalDate.of(2023,12,1));
        reservation.setEnd(LocalDate.of(2023,12,7));

        Guest guest = new Guest();
        guest.setId(1);
        reservation.setGuest(guest);

        reservation.setTotal(new BigDecimal(150));

        reservation = repository.add(host, reservation);

        assertEquals(2, reservation.getId());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStart(LocalDate.of(2023,12,2));
        reservation.setEnd(LocalDate.of(2023,12,8));
        Guest guest = new Guest();
        guest.setId(1);
        reservation.setGuest(guest);
        reservation.setTotal(new BigDecimal(100));

        boolean success = repository.update(host, reservation);
        assertTrue(success);
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(1);

        boolean actual = repository.delete(host, reservation);
        assertTrue(actual);
    }

}