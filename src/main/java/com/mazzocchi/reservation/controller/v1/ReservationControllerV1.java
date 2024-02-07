package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.*;
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
    public ResponseEntity <String> saveReservation(@Valid @RequestBody ReservationDto reservationDto) {
        // Saves the reservation. If the reservation is saved, returns a 201. If not, returns a 400

            reservationService.saveReservation(reservationMapper.dtoToReservation(reservationDto));
            return new ResponseEntity<>("Reservation saved", HttpStatus.CREATED);

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

            final Page<Reservation> reservations = reservationService.findAllReservations(State.valueOf(state), PageRequest.of(page, size));

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

    }




    @GetMapping("/{id}")
    @Operation(summary = "Find a reservation by its id", description = "Find a reservation by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ReservationDto> findReservationById(@PathVariable Long id) {

            // Tries to find the reservation by its id and returns it if found. If not, returns a 404
            final Reservation reservation = reservationService.findReservationById(id);
            return new ResponseEntity<>(reservationMapper.reservationToDto(reservation), HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a reservation by its id", description = "Update a reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity <String> updateReservationById(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {

            // If the reservation is not found, return a not found
            Reservation reservation = reservationService.findReservationById(id);
            reservationService.updateReservationById(id, reservationMapper.dtoToReservation(reservationDto));
            return new ResponseEntity<>("Reservation updated", HttpStatus.OK);

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

            reservationService.cancelReservationById(id);
            return new ResponseEntity<>("Reservation canceled", HttpStatus.OK);

    }

    @PutMapping("/add-menu/{reservationId}/{menuId}")
    @Operation(summary = "Add a menu to a reservation", description = "Add a menu to a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu added to reservation"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "404", description = "Menu is inactive"),
            @ApiResponse(responseCode = "404", description = "Reservation is inactive"),
            @ApiResponse(responseCode = "404", description = "Menu is already assigned to this reservation"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity <String> addMenuToReservation(@PathVariable Long reservationId, @PathVariable Long menuId) {

            reservationService.addMenuToReservation(reservationId, menuId);
            return new ResponseEntity<>("Menu added to reservation", HttpStatus.OK);

    }

}
