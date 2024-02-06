package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
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
    @Operation(summary = "Find all menus", description = "Find all menus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found menus"),
    })
    public ResponseEntity <List<MenuDto>> findAllMenus() {
        final List<Menu> menus = menuService.findAllMenus();

        return new ResponseEntity<>(menus.stream().map(menuMapper::menuToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a menu by its id", description = "Find a menu by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menu"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Long id) {
        // Tries to find the menu by its id and returns it if found. If not, returns a 404
        final Menu menu = menuService.findMenuById(id);

        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menuMapper.menuToDto(menu), HttpStatus.OK);
    }
}
