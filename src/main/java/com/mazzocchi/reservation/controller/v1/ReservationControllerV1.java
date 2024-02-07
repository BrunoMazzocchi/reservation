package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.data.domain.*;
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
            @ApiResponse(responseCode = "500", description = "Internal Server Error")


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
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<PagedResponse<ReservationDto>> findAllMenus(
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size,
            @RequestHeader(defaultValue = "ACTIVE") String state
    ) {
        try {
            final Page<Reservation> reservations = reservationService.findAllReservations(State.valueOf(state), PageRequest.of(page, size));

            if (reservations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ReservationDto> reservationDto = reservations.getContent().stream().map(reservationMapper::reservationToDto).collect(Collectors.toList());

            PagedResponse<ReservationDto> response = new PagedResponse<>(
                    reservationDto,
                    reservations.getNumber(),
                    reservations.getSize(),
                    reservations.getTotalElements(),
                    reservations.getTotalPages(),
                    reservations.isLast()
            );


            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/{id}")
    @Operation(summary = "Find a reservation by its id", description = "Find a reservation by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ReservationDto> findReservationById(@PathVariable Long id) {
        try {
            // Tries to find the reservation by its id and returns it if found. If not, returns a 404
            final Reservation reservation = reservationService.findReservationById(id);
            if (reservation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reservationMapper.reservationToDto(reservation), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a reservation by its id", description = "Update a reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity <String> updateReservationById(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        try {
            // If the reservation is not found, return a not found
            Reservation reservation = reservationService.findReservationById(id);
            if (reservation == null) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            }
            reservationService.updateReservationById(id, reservationMapper.dtoToReservation(reservationDto));
            return new ResponseEntity<>("Reservation updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel/{id}")
    @Operation(summary = "Cancel a reservation by its id", description = "Cancel a reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation canceled"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "200", description = "Reservation already canceled"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity <String> cancelReservationById(@PathVariable Long id) {
        try {
            // If the reservation is already canceled, return a not found
            Reservation reservation = reservationService.findReservationById(id);
            if (reservation == null ) {
                return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
            } else if (reservation.getState() == State.INACTIVE) {
                return new ResponseEntity<>("Reservation already canceled", HttpStatus.OK);
            }
            reservationService.cancelReservationById(id);
            return new ResponseEntity<>("Reservation canceled", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
