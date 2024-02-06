package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationControllerV1 {
    final IReservationService reservationService;

    final IReservationMapper reservationMapper = IReservationMapper.INSTANCE;

    public ReservationControllerV1(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/save")
    public ResponseEntity <String> saveReservation(@RequestBody ReservationDto reservationDto) {
        reservationService.saveReservation(reservationMapper.dtoToReservation(reservationDto));
        return new ResponseEntity<>("Reservation saved", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity <List<ReservationDto>> findAllReservations() {
        final List<Reservation> reservations = reservationService.findAllReservations();
        return new ResponseEntity<>(reservations.stream().map(reservationMapper::reservationToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> findReservationById(@PathVariable Long id) {
        final Reservation reservation = reservationService.findReservationById(id);
        return new ResponseEntity<>(reservationMapper.reservationToDto(reservation), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <String> updateReservationById(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        reservationService.updateReservationById(id, reservationMapper.dtoToReservation(reservationDto));
        return new ResponseEntity<>("Reservation updated", HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity <String> cancelReservationById(@PathVariable Long id) {
        reservationService.cancelReservationById(id);
        return new ResponseEntity<>("Reservation canceled", HttpStatus.OK);
    }

}
