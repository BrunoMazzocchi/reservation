package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.reserve.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Qualifier("V1")
public class ReservationServiceV1Impl implements IReservationService {
    private final IReserveRepository reservationRepository;

    public ReservationServiceV1Impl(IReserveRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
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
}
