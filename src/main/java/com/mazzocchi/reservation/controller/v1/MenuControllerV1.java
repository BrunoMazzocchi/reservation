package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuControllerV1 {

    private final IMenuService menuService;

    private final IMenuMapper menuMapper = IMenuMapper.INSTANCE;

    public MenuControllerV1(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/all")
    public ResponseEntity <List<MenuDto>> findAllMenus() {
        final List<Menu> menus = menuService.findAllMenus();

        return new ResponseEntity<>(menus.stream().map(menuMapper::menuToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Long id) {
        final Menu menu = menuService.findMenuById(id);

        return new ResponseEntity<>(menuMapper.menuToDto(menu), HttpStatus.OK);
    }
}
