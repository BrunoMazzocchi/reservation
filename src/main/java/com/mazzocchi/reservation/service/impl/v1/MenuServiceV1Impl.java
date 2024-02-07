package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Service
@Qualifier("V1")
public class MenuServiceV1Impl implements  IMenuService{
    private final IMenuRepository menuRepository;

    final IMenuMapper menuMapper = IMenuMapper.INSTANCE;

    public MenuServiceV1Impl(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public Page<MenuDto> findAllMenus(State state, Pageable pageable) {
        Page<Menu> menus = menuRepository.findByState(state, pageable);
        return menus.map(menuMapper::menuToDto);
    }

    @Override
    public MenuDto findMenuById(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found with id " + id));
        return menuMapper.menuToDto(menu);
    }

}
