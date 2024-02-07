package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.reservation.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface IReservationMapper {
    IReservationMapper INSTANCE = Mappers.getMapper(IReservationMapper.class);
    @Mapping(target = "menu", ignore = true)
    Reservation dtoToReservation(ReservationDto reservationDto);

    @Mapping(target = "menu", ignore = true)
    ReservationResponseDto reservationToResponseDto(Reservation reservation);

}
