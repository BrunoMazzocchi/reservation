package com.mazzocchi.reservation.controller.v1;

import com.mazzocchi.reservation.dto.*;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuControllerV1 {

    private final IMenuService menuService;
    private final IDinnerService dinnerService;


    public MenuControllerV1(IMenuService menuService, IDinnerService dinnerService) {
        this.menuService = menuService;
        this.dinnerService = dinnerService;
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
            final Page<MenuDto> menus = menuService.findAllMenus(State.valueOf(state),  PageRequest.of(page, size));




            PagedResponse<MenuDto> response = new PagedResponse<>(
                    menus.getContent(),
                    menus.getNumber(),
                    menus.getSize(),
                    menus.getTotalElements(),
                    menus.getTotalPages(),
                    menus.isLast()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @GetMapping("/{id}")
    @Operation(summary = "Find a menu by its id", description = "Find a menu by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menu"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<MenuDto> findMenuById(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.findMenuById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/dinner")
    @Operation(summary = "Find all dinners by menu id", description = "Find all dinners by menu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all dinners"),
            @ApiResponse(responseCode = "404", description = "No dinners found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<PagedResponse<DinnerDto>> findAllDinnersByMenuId(
            @PathVariable Long id,
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size
    ) {

            final Page<DinnerDto> dinners = dinnerService.findAllDinnersByMenuId(id, PageRequest.of(page, size));


            PagedResponse<DinnerDto> response = new PagedResponse<>(
                    dinners.getContent(),
                    dinners.getNumber(),
                    dinners.getSize(),
                    dinners.getTotalElements(),
                    dinners.getTotalPages(),
                    dinners.isLast()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{menuId}/dinner/{dinnerId}")
    @Operation(summary = "Find a dinner by its id and menu id", description = "Find a dinner by its id and menu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the dinner"),
            @ApiResponse(responseCode = "404", description = "Dinner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<DinnerDto> findDinnerByIdAndMenuId(
            @PathVariable Long menuId,
            @PathVariable Long dinnerId
    ) {

            return new ResponseEntity<>(dinnerService.findDinnerByIdAndMenuId(dinnerId, menuId), HttpStatus.OK);
    }

}
