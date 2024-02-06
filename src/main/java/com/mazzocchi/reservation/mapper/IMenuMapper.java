package com.mazzocchi.reservation.mapper;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.models.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper(componentModel = "spring", uses = {IMenuMapper.class})
public interface IMenuMapper {
    IMenuMapper INSTANCE = Mappers.getMapper(IMenuMapper.class);

    @Mapping(source = "id", target = "id")
    MenuDto menuToDto(Menu menu);

    @Mapping(source = "id", target = "id")
    Menu dtoToMenu(MenuDto menuDto);
}
