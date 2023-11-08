package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.nio.file.Path;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {
    private String directoryPath;

    public ReservationFileRepository(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<Reservation> findByHost(Host host) {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) {
        return false;
    }

    @Override
    public boolean deleteById(int reservationId) {
        return false;
    }

    private Reservation findById(List<Reservation> reservations, int num) {
        return null;
    }
    private String serialize(Reservation reservation) {
        return null;
    }
    private Reservation deserialize(String value) {
        return null;
    }
    private Path getFilePath(Host host) {
        return null;
    }
    private int getNextId(Path path) {
        return 0;
    }
    private void appendToFile(Path path, Reservation reservation) {
        return;
    }
    private void breakToNewLine(Path path) {
        return;
    }
    private boolean endsWithNewLine(Path path) {
        return false;
    }

}
