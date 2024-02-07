package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Qualifier("V1")
public class MenuServiceV1Impl implements  IMenuService{
    private final IMenuRepository menuRepository;

    public MenuServiceV1Impl(IMenuRepository menuRepository, IDinnerRepository dinnerRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public Page<Menu> findAllMenus(State state, Pageable pageable) { return menuRepository.findByState(state, pageable); }

    @Override
    public Menu findMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

}
