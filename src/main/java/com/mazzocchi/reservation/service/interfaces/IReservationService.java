package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.dto.reservation.*;
import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IReservationService {
    /// Save a new reservation
    void saveReservation(ReservationDto reservation);

    /// Find all reservations
    Page<ReservationResponseDto> findAllReservations(State state, Pageable pageable);

    /// Find a reservation by its id
    ReservationResponseDto findReservationById(Long id);

    /// Update a reservation by its id
    void updateReservationById(Long id, ReservationDto reservation);

    /// Cancel a reservation by its id
    void cancelReservationById(Long id);

    /// Add a menu to a reservation
    void addMenuToReservation(Long reservationId, Long menuId);
}
