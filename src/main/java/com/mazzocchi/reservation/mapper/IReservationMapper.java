package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.reservation.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface IReservationMapper {
    IReservationMapper INSTANCE = Mappers.getMapper(IReservationMapper.class);
    @Mapping(source = "id", target = "id")
    ReservationDto reservationToDto(Reservation reservation);
    @Mapping(source = "id", target = "id")
    Reservation dtoToReservation(ReservationDto reservationDto);

    @Mapping(source = "id", target = "id")
    ReservationResponseDto reservationToResponseDto(Reservation reservation);

}
