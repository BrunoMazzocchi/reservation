package com.mazzocchi.reservation.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mazzocchi.reservation.models.Menu;
import com.mazzocchi.reservation.models.State;

public interface IMenuService {
    Page<Menu> findAllMenus(State state, Pageable pageable);

    Menu findMenuById(Long id);

}
