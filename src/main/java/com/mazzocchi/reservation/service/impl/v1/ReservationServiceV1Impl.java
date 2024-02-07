package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.repository.reserve.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Qualifier("V1")
public class ReservationServiceV1Impl implements IReservationService {
    private final IReserveRepository reservationRepository;

    private final IMenuRepository menuRepository;

    public ReservationServiceV1Impl(IReserveRepository reservationRepository, IMenuRepository menuRepository) {
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
    }


    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public Page<Reservation> findAllReservations(State state,  Pageable pageable) {
        return reservationRepository.findByState(state,  pageable);
    }


    @Override
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public void updateReservationById(Long id, Reservation reservation) {
          // Updates the reservation with the incoming reservation data
        Reservation existingReservation = reservationRepository.findById(id).orElse(null);
        if (existingReservation != null) {
            existingReservation.setDateReserve(reservation.getDateReserve());
            existingReservation.setState(reservation.getState());
            existingReservation.setMenu(reservation.getMenu());
            existingReservation.setCustomerName(reservation.getCustomerName());
            existingReservation.setCustomerNumber(reservation.getCustomerNumber());
            reservationRepository.save(existingReservation);
        }
    }

    @Override
    public void cancelReservationById(Long id) {
        // Updates the state to inactive
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            reservation.setState(State.INACTIVE);
            reservationRepository.save(reservation);
        }
    }

    @Override
    @Transactional
    public void addMenuToReservation(Long reservationId, Long menuId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id " + reservationId));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found with id " + menuId));

        if (menu.getState() == State.INACTIVE) {
            throw new InactiveException("Menu is inactive");
        }

        if (reservation.getState() == State.INACTIVE) {
            throw new InactiveException("Reservation is inactive");
        }

        if (reservation.getMenu() != null && reservation.getMenu().getId().equals(menuId)) {
            throw new AlreadyAssignedException("Menu is already assigned to this reservation");
        }

        reservation.setMenu(menu);

        reservationRepository.save(reservation);
    }
}
