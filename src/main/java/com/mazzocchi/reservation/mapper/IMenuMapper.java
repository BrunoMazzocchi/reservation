package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface IMenuMapper {
    IMenuMapper INSTANCE = Mappers.getMapper(IMenuMapper.class);

    @Mapping(source = "id", target = "id")
    MenuDto menuToDto(Menu menu);
}
