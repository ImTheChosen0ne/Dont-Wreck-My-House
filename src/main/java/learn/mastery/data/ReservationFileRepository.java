package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private String directoryPath;
    private static final String DELIMITER = ",";

    public ReservationFileRepository(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Reservation> findByHost(Host host) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host).toFile()))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }

        return result;
    }

    @Override
    public Reservation add(Host host, Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(host);
        Path filePath = getFilePath(host);
        int nextId = getNextId(filePath);
        reservation.setId(nextId);
        all.add(reservation);
        appendToFile(filePath, all);
        return reservation;
    }
    @Override
    public boolean update(Reservation reservation) throws DataException {
        Reservation existingReservation = findById(findByHost(reservation.getHost()),reservation.getId());

        if (existingReservation != null) {
            existingReservation.setStart(reservation.getStart());
            existingReservation.setEnd(reservation.getEnd());
            existingReservation.setGuest(reservation.getGuest());
            existingReservation.setTotal(reservation.getTotal());

            appendToFile(getFilePath(reservation.getHost()), findByHost(reservation.getHost()));
            return true;
        }

        return false;
    }
    @Override
    public boolean delete(Reservation reservation) throws DataException {
        Reservation existingReservation = findById(findByHost(reservation.getHost()), reservation.getId());
        if (existingReservation != null) {
            findByHost(reservation.getHost()).remove(existingReservation);
            appendToFile(getFilePath(reservation.getHost()), findByHost(reservation.getHost()));
            return true;
        }
        return false;
    }
    private Reservation findById(List<Reservation> all, int reservationId) {
        for (Reservation reservation : all) {
            if (reservation.getId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }
    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getGuest().getId(),
                reservation.getTotal());
    }
    private Reservation deserialize(String[] fields) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart(LocalDate.parse(fields[1]));
        result.setEnd(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        result.setTotal(new BigDecimal(fields[4]));
        return result;
    }
    private Path getFilePath(Host host) {
        return Paths.get(directoryPath, host.getId() + ".csv");
    }
    private int getNextId(Path path) throws DataException {
        List<Reservation> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    reservations.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            throw new DataException(ex);
        }

        return reservations.stream()
                .max(Comparator.comparing(Reservation::getId))
                .map(reservation -> reservation.getId() + 1)
                .orElse(1);
    }
    private void appendToFile(Path path, List<Reservation> reservations) throws DataException {
        try (PrintWriter writer = new PrintWriter(path.toString())) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
    private String breakToNewLine(Reservation reservation) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append(reservation.getId()).append(DELIMITER);
        buffer.append(reservation.getStart()).append(DELIMITER);
        buffer.append(reservation.getEnd()).append(DELIMITER);
        buffer.append(reservation.getGuest().getId()).append(DELIMITER);
        buffer.append(reservation.getTotal());
        return buffer.toString();
    }
    private boolean endsWithNewLine(Path path) {
        return false;
    }

}
