package com.mazzocchi.reservation.repository.reserve;

import com.mazzocchi.reservation.models.*;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.sql.Date;
import java.util.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IReserveRepositoryTest {

    @Autowired
    private IReserveRepository iReserveRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByState() {
        // Arrange
        Menu menu = new Menu();
        Dinner dinner = new Dinner();
        dinner.setName("Dinner");
        dinner.setPrice(100000);
        dinner.setDescription("Dinner");
        menu.setName("Menu");
        menu.setPrice(100000);
        menu.setState(State.ACTIVE);
        menu.setDinners(List.of(dinner));
        entityManager.persist(dinner);
        entityManager.persist(menu);
        State state = State.ACTIVE;
        Reservation reservation = new Reservation();
        reservation.setState(state);
        reservation.setCustomerName("John Doe");
        reservation.setCustomerNumber("1234567890");
        reservation.setDateReserve(new Date(System.currentTimeMillis()));
        reservation.setMenu(menu);

        entityManager.persist(reservation);
        entityManager.flush();

        // Act
        Page<Reservation> result = iReserveRepository.findByState(state, PageRequest.of(0, 10));

        // Assert
        Assertions.assertEquals(5, result.getTotalElements());
    }
}
