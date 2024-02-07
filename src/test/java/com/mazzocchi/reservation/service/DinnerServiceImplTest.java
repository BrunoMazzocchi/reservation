package com.mazzocchi.reservation.service;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.service.impl.v1.*;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.repository.menu.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DinnerServiceImplTest {

    @Mock
    private IDinnerRepository dinnerRepository;

    @InjectMocks
    @Mock
    private DinnerServiceV1Impl dinnerService;

    @Test
    public void testFindAllDinnersByMenuId() {
        // Arrange
        Long menuId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        when(dinnerRepository.findDinnersByMenuId(menuId, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<DinnerDto> result = dinnerService.findAllDinnersByMenuId(menuId, pageable);

        // Assert
        assertNotNull(result);
        verify(dinnerRepository, times(1)).findDinnersByMenuId(menuId, pageable);
    }

    @Test
    public void testFindAllDinnersByMenuIdFailed() {
        // Arrange
        Long menuId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        when(dinnerRepository.findDinnersByMenuId(menuId, pageable)).thenThrow(new RuntimeException());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> dinnerService.findAllDinnersByMenuId(menuId, pageable));
    }

    @Test
    public void testFindDinnerByIdAndMenuId() {
        // Arrange
        Long menuId = 1L;
        Long dinnerId = 1L;
        DinnerDto mockDinnerDto = new DinnerDto();
        // Set properties on mockDinnerDto...

        when(dinnerService.findDinnerByIdAndMenuId(dinnerId, menuId)).thenReturn(mockDinnerDto);

        // Act
        DinnerDto result = dinnerService.findDinnerByIdAndMenuId(dinnerId, menuId);

        // Assert
        assertNotNull(result);
        assertEquals(mockDinnerDto, result);
        verify(dinnerService, times(1)).findDinnerByIdAndMenuId(dinnerId, menuId);
    }

    @Test
    public void testFindDinnerByIdAndMenuIdFailed() {
        // Arrange
        Long menuId = 1L;
        Long dinnerId = 1L;
        when(dinnerRepository.findDinnerByIdAndMenuId(dinnerId, menuId)).thenThrow(new NotFoundException("Dinner not found with id " + dinnerId + " and menuId " + menuId));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> dinnerService.findDinnerByIdAndMenuId(dinnerId, menuId));
    }

    @Test
    public void testFindDinnerById() {
        // Arrange
        Long dinnerId = 1L;
        List<Menu> menus = new ArrayList<>();
        when(dinnerRepository.findById(dinnerId)).thenReturn(Optional.of(new Dinner(
                1L,
                "name",
                "description",
                1.0,
                menus
        )));

        // Act
        DinnerDto result = dinnerService.findDinnerById(dinnerId);

        // Assert
        assertNotNull(result);
        verify(dinnerRepository, times(1)).findById(dinnerId);
    }

    @Test
    public void testFindDinnerByIdFailed() {
        // Arrange
        Long dinnerId = 1L;
        when(dinnerRepository.findById(dinnerId)).thenThrow(new NotFoundException("Dinner not found with id " + dinnerId));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> dinnerService.findDinnerById(dinnerId));
    }

}