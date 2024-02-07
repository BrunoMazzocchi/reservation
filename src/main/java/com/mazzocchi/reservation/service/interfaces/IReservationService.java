package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IReservationService {
    /// Save a new reservation
    void saveReservation(Reservation reservation);

    /// Find all reservations
    Page<Reservation> findAllReservations(State state, Pageable pageable);

    /// Find a reservation by its id
    Reservation findReservationById(Long id);

    /// Update a reservation by its id
    void updateReservationById(Long id, Reservation reservation);

    /// Cancel a reservation by its id
    void cancelReservationById(Long id);
}
