package com.mazzocchi.reservation.service;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.reservation.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.reserve.*;
import com.mazzocchi.reservation.service.impl.v1.*;
import com.mazzocchi.reservation.repository.menu.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;

import java.sql.Date;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceImplTest {
    @Mock
    private IReserveRepository reservationRepository;

    @Mock
    private IMenuRepository menuRepository;

    @InjectMocks
    private ReservationServiceV1Impl reservationService;

    @Test
    public void saveReservation() {
        // Arrange
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setMenuId(1L);
        Menu menu = new Menu();
        menu.setState(State.ACTIVE);
        when(menuRepository.findById(reservationDto.getMenuId())).thenReturn(Optional.of(menu));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Act
        reservationService.saveReservation(reservationDto);

        // Assert
        verify(menuRepository, times(1)).findById(1L);
    }

    @Test
    public void saveReservationFailed() {
        // Arrange
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setMenuId(1L);
        Menu menu = new Menu();
        menu.setState(State.INACTIVE);
        when(menuRepository.findById(reservationDto.getMenuId())).thenReturn(Optional.of(menu));

        // Act and Assert
        assertThrows(InactiveException.class, () -> reservationService.saveReservation(reservationDto));
    }

    @Test
    public void findAllReservations() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(reservationRepository.findByState(State.ACTIVE, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<ReservationResponseDto> result = reservationService.findAllReservations(State.ACTIVE, pageable);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).findByState(State.ACTIVE, pageable);
    }


    @Test
    public void findAllReservationsFailed() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(reservationRepository.findByState(State.ACTIVE, pageable)).thenThrow(new RuntimeException());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> reservationService.findAllReservations(State.ACTIVE, pageable));
    }

    @Test
    public void findReservationById() {
        // Arrange
        Long id = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        // Act
        ReservationResponseDto result = reservationService.findReservationById(id);

        // Assert
        assertNotNull(result);
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    public void findReservationByIdFailed() {
        // Arrange
        Long id = 1L;
        when(reservationRepository.findById(id)).thenThrow(new NotFoundException("Reservation not found with id " + id));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> reservationService.findReservationById(id));
    }

    @Test
    public void updateReservationById() {
        // Arrange
        Long id = 1L;
        ReservationDto reservationDto = new ReservationDto(
                1L,
                "customerName",
                "customerNumber",
                new Date(2021, 1, 1),
                "ACTIVE",
                1L
        );
        reservationDto.setMenuId(1L);
        Reservation reservation = new Reservation();
        reservation.setState(State.ACTIVE);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        when(menuRepository.findById(reservationDto.getMenuId())).thenReturn(Optional.of(new Menu()));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Act
        reservationService.updateReservationById(id, reservationDto);

        // Assert
        verify(reservationRepository, times(1)).findById(id);
        verify(menuRepository, times(1)).findById(1L);

    }

    @Test
    public void updateReservationByIdFailed() {
        // Arrange
        Long id = 1L;
        ReservationDto reservationDto = new ReservationDto(
                1L,
                "customerName",
                "customerNumber",
                new Date(2021, 1, 1),
                "ACTIVE",
                1L
        );
        reservationDto.setMenuId(1L);
        Reservation reservation = new Reservation();
        reservation.setState(State.INACTIVE);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        // Act and Assert
        assertThrows(InactiveException.class, () -> reservationService.updateReservationById(id, reservationDto));
    }


    @Test
    public void cancelReservationById() {
        // Arrange
        Long id = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Act
        reservationService.cancelReservationById(id);

        // Assert
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    public void cancelReservationByIdFailed() {
        // Arrange
        Long id = 1L;
        when(reservationRepository.findById(id)).thenThrow(new NotFoundException("Reservation not found with id " + id));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> reservationService.cancelReservationById(id));
    }

    @Test
    public void addMenuToReservation() {
        // Arrange
        Long reservationId = 1L;
        Long menuId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(menuRepository.findById(1L)).thenReturn(Optional.of(new Menu()));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Act
        reservationService.addMenuToReservation(reservationId, menuId);

        // Assert
        verify(reservationRepository, times(1)).findById(1L);
    }
}
