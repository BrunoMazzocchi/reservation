package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.models.*;

import java.util.*;

public interface IReservationService {
    /// Save a new reservation
    void saveReservation(Reserve reservation);

    /// Find all reservations
    List<Reserve> findAllReservations();

    /// Find a reservation by its id
    Reserve findReservationById(Long id);

    /// Update a reservation by its id
    void updateReservationById(Long id, Reserve reservation);

    /// Cancel a reservation by its id
    void cancelReservationById(Long id);
}
