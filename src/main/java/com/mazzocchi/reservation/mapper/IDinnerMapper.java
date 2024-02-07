package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface IDinnerMapper {
    IDinnerMapper INSTANCE = Mappers.getMapper(IDinnerMapper.class);

    @Mapping(source = "id", target = "id")
    DinnerDto dinnerToDto(Dinner dinner);

    @Mapping(source = "id", target = "id")
    Dinner dtoToDinner(DinnerDto dinnerDto);
}
