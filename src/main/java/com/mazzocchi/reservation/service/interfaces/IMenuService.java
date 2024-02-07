package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.dto.menu.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mazzocchi.reservation.models.Menu;
import com.mazzocchi.reservation.models.State;

public interface IMenuService {
    Page<MenuDto> findAllMenus(State state, Pageable pageable);

    MenuDto findMenuById(Long id);

}
