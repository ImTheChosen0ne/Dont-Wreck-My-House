package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private HostRepository hostRepository;
    private GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByHost(Host host) {
        return reservationRepository.findByHost(host);
    }

    public Result add(Host host, Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
                return result;
        }

    // The reservation may never overlap existing reservation dates.
        List<Reservation> reservations = reservationRepository.findByHost(host);
        for (Reservation r : reservations) {
            if (reservation.getStart().isBefore(r.getEnd()) &&
                    reservation.getEnd().isAfter(r.getStart())) {
                result.addErrorMessage("Existing Reservation");
                return result;
            }
        }

        reservationRepository.add(host, reservation);
        result.setPayload(reservation);

        return result;
    }

    public Result update(Host host, Reservation reservation) throws DataException {
        Result result = validate(reservation);

        if (reservation.getStart().isBefore(LocalDate.now()) || reservation.getEnd().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot update a past reservation.");
        }

        if (result.isSuccess()) {
            if (reservationRepository.update(host, reservation)) {
                result.setPayload(reservation);
            } else {
                result.addErrorMessage("Reservation was not found.");
            }
        }
        return result;
    }
    public Result delete(Host host, Reservation reservation) throws DataException {
        Result result = new Result();
        if (reservation.getStart().isBefore(LocalDate.now()) || reservation.getEnd().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot cancel a reservation in the past.");
        } else if (!reservationRepository.delete(host, reservation)) {
            result.addErrorMessage("Reservation was not found.");
        }
        return result;
    }
    private Result validate(Reservation reservation) {
        Result<Reservation> result = new Result<>();

    // Guest, host, and start and end dates are required.
        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
            return result;
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is required.");
            return result;
        }

        if (reservation.getStart() == null) {
            result.addErrorMessage("Start Date is required.");
            return result;
        }

        if (reservation.getEnd() == null) {
            result.addErrorMessage("End Date is required.");
            return result;
        }

        // The guest and host must already exist in the "database". Guests and hosts cannot be created.
        if (reservation.getHost().getId() == null
                || hostRepository.getHostByEmail(reservation.getHost().getEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepository.getGuestById(reservation.getGuest().getId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }

        // The start date must come before the end date.
        if (reservation.getStart().isAfter(reservation.getEnd())) {
            result.addErrorMessage("Start date must be before end date.");
            return result;
        }

        // The start date must be in the future.
        if (reservation.getStart().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date must be in the future.");
            return result;
        }

        // Reservation Start and end cannot be on same day
        if (reservation.getStart().isEqual(reservation.getEnd())) {
            result.addErrorMessage("Cannot have Start Date and End date on same day.");
            return result;
        }

        return result;
    }
}
