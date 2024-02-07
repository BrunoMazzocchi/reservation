package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.reservation.*;
import com.mazzocchi.reservation.mapper.*;
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


    final IReservationMapper reservationMapper = IReservationMapper.INSTANCE;

    public ReservationServiceV1Impl(IReserveRepository reservationRepository, IMenuRepository menuRepository) {
        this.reservationRepository = reservationRepository;
        this.menuRepository = menuRepository;
    }


    @Override
    public void saveReservation(ReservationDto reservationDto) {
        Menu menu = menuRepository.findById(reservationDto
                .getMenuId())
                .orElseThrow(() -> new NotFoundException("Menu not found with id " + reservationDto.getMenuId()));

        if (menu.getState() == State.INACTIVE) {
            throw new InactiveException("Menu is inactive");
        }

        Reservation newReservation = reservationMapper.dtoToReservation(reservationDto);

        newReservation.setMenu(menu);

        reservationRepository.save(newReservation);
    }

    @Override
    public Page<ReservationResponseDto> findAllReservations(State state,  Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByState(state, pageable);

        return reservations.map(reservationMapper::reservationToResponseDto);
    }


    @Override
    public ReservationResponseDto findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id " + id));
        return reservationMapper.reservationToResponseDto(reservation);
    }

    @Override
    public void updateReservationById(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id " + id));

        if (reservation.getState() == State.INACTIVE) {
            throw new InactiveException("Reservation is inactive");
        }

        // Compares the changes in the reservationDto and the current reservation to update the values
        if (reservationDto.getMenuId() != null) {
            Menu newMenu = menuRepository.findById(reservationDto.getMenuId())
                    .orElseThrow(() -> new NotFoundException("Menu not found with id " + reservationDto.getMenuId()));

            if (newMenu.getState() == State.INACTIVE) {
                throw new InactiveException("Menu is inactive");
            }

            reservation.setMenu(newMenu);
        }

        reservation.setDateReserve(reservationDto.getDateReserve());
        reservation.setCustomerNumber(reservationDto.getCustomerNumber());
        reservation.setState(State.valueOf(reservationDto.getState()));
        reservation.setCustomerName(reservationDto.getCustomerName());

        reservationRepository.save(reservation);

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

        if (reservation.getState() == State.INACTIVE) {
            throw new InactiveException("Reservation is inactive");
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found with id " + menuId));

        if (menu.getState() == State.INACTIVE) {
            throw new InactiveException("Menu is inactive");
        }

        if (reservation.getMenu() != null && reservation.getMenu().getId().equals(menuId)) {
            throw new AlreadyAssignedException("Menu is already assigned to this reservation");
        }

        reservation.setMenu(menu);
        reservationRepository.save(reservation);
    }
}
