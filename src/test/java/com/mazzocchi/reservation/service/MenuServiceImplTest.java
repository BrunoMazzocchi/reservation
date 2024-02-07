package com.mazzocchi.reservation.service;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.impl.v1.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {

    @Mock
    private IMenuRepository menuRepository;

    @InjectMocks
    private MenuServiceV1Impl menuService;

    @Test
    public void testFindAllMenus() {
        // Arrange
        when(menuRepository.findByState(State.ACTIVE, Pageable.ofSize(10)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Page<MenuDto> result = menuService.findAllMenus(State.ACTIVE, Pageable.ofSize(10));

        // Assert
        assertNotNull(result);

        verify(menuRepository, times(1)).findByState(State.ACTIVE, Pageable.ofSize(10));
    }

    @Test
    public void testFindMenuById() {
        Menu menu = new Menu(
                1L,
                "name",
                40,
                State.ACTIVE,
                new ArrayList<>());

        // Arrange
        Long menuId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // Act
        MenuDto result = menuService.findMenuById(menuId);

        // Assert
        assertNotNull(result);
        verify(menuRepository, times(1)).findById(menuId);
    }

    @Test
    public void testFindMenuByIdNotFound() {
        // Arrange
        Long menuId = 1L;
        when(menuRepository.findById(menuId)).thenThrow(new NotFoundException("Menu not found with id " + menuId));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> menuService.findMenuById(menuId));
    }

    @Test
    public void testFindAllMenusByStateNotFound() {
        // Arrange
        State state = State.ACTIVE;
        Pageable pageable = PageRequest.of(0, 10);
        when(menuRepository.findByState(State.ACTIVE, Pageable.ofSize(10)))
                .thenThrow(new NotFoundException("Menu not found with state " + state));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> menuService.findAllMenus(state, pageable));
    }
}
