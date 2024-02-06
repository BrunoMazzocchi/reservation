package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
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
    @Operation(summary = "Save a reservation", description = "Save a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation saved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),


    })
    public ResponseEntity <String> saveReservation(@RequestBody ReservationDto reservationDto) {
        // Saves the reservation. If the reservation is saved, returns a 201. If not, returns a 400
        try {
            reservationService.saveReservation(reservationMapper.dtoToReservation(reservationDto));
            return new ResponseEntity<>("Reservation saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Find all reservations", description = "Find all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found reservations"),
    })
    public ResponseEntity <List<ReservationDto>> findAllReservations() {
        final List<Reservation> reservations = reservationService.findAllReservations();
        return new ResponseEntity<>(reservations.stream().map(reservationMapper::reservationToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a reservation by its id", description = "Find a reservation by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationDto> findReservationById(@PathVariable Long id) {
        // Tries to find the reservation by its id and returns it if found. If not, returns a 404
        final Reservation reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationMapper.reservationToDto(reservation), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a reservation by its id", description = "Update a reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity <String> updateReservationById(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        reservationService.updateReservationById(id, reservationMapper.dtoToReservation(reservationDto));
        return new ResponseEntity<>("Reservation updated", HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    @Operation(summary = "Cancel a reservation by its id", description = "Cancel a reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation canceled"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity <String> cancelReservationById(@PathVariable Long id) {
        reservationService.cancelReservationById(id);
        return new ResponseEntity<>("Reservation canceled", HttpStatus.OK);
    }

}
