package com.mazzocchi.reservation.controller.v1;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/dinner")
@Tag(name = "Dinner Controller", description = "Dinner Controller")
public class DinnerControllerV1 {
    final private IDinnerService dinnerService;

    public DinnerControllerV1(IDinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a dinner by id", description = "Find a dinner by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the dinner"),
            @ApiResponse(responseCode = "404", description = "Dinner not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<DinnerDto> findDinnerById(@PathVariable Long id) {
        return ResponseEntity.ok(dinnerService.findDinnerById(id));
    }
}
