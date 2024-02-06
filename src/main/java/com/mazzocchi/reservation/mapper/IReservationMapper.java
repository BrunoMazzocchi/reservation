package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface IReservationMapper {
    IReservationMapper INSTANCE = Mappers.getMapper(IReservationMapper.class);
    @Mapping(source = "id", target = "id")
    ReserveDto  reservationToDto(Reserve reservation);
    @Mapping(source = "id", target = "id")
    Reserve dtoToReservation(ReserveDto reservationDto);
}
