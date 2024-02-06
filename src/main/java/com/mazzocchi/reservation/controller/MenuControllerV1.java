package com.mazzocchi.reservation.controller;

import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuControllerV1 {

    private final IMenuService menuService;

    public MenuControllerV1(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/all")
    public List<Menu> findAllMenus() {
        return menuService.findAllMenus();
    }
}
