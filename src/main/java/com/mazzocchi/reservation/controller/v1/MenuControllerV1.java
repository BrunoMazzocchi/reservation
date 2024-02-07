package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.data.domain.*;
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
    @Operation(summary = "Find all menus", description = "Find all paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all menus"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<PagedResponse<MenuDto>> findAllMenus(
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size,
            @RequestHeader(defaultValue = "ACTIVE") String state
    ) {
        try {
            final Page<Menu> menus = menuService.findAllMenus(State.valueOf(state),  PageRequest.of(page, size));

            if (menus.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<MenuDto> menuDto = menus.getContent().stream().map(menuMapper::menuToDto).collect(Collectors.toList());

            PagedResponse<MenuDto> response = new PagedResponse<>(
                    menuDto,
                    menus.getNumber(),
                    menus.getSize(),
                    menus.getTotalElements(),
                    menus.getTotalPages(),
                    menus.isLast()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}")
    @Operation(summary = "Find a menu by its id", description = "Find a menu by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menu"),
            @ApiResponse(responseCode = "404", description = "Menu not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Long id) {
        try {
            // Tries to find the menu by its id and returns it if found. If not, returns a 404
            final Menu menu = menuService.findMenuById(id);

            if (menu == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(menuMapper.menuToDto(menu), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
