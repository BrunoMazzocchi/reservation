package com.mazzocchi.reservation.service.impl;

import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Qualifier("V1")
public class MenuServiceV1Impl implements  IMenuService{
    private final IMenuRepository menuRepository;

    public MenuServiceV1Impl(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findMenuById(int id) {
        return findMenuById(id);
    }
}
