package com.mazzocchi.reservation.repository.menu;

import com.mazzocchi.reservation.models.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IDinnerRepositoryTest {

    @Autowired
    private IDinnerRepository iDinnerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindDinnersByMenuId() {
        // Arrange
        Long menuId = 1L;
        Menu menu = new Menu();
        Dinner dinner = new Dinner();
        dinner.setName("Dinner");
        dinner.setPrice(100000);
        dinner.setDescription("Dinner");
        menu.setName("Menu");
        menu.setPrice(100000);
        menu.setState(State.ACTIVE);
        menu.setDinners(List.of(dinner));
        entityManager.persist(menu);
        entityManager.persist(dinner);
        entityManager.flush();

        // Act
        Page<Dinner> result = iDinnerRepository.findDinnersByMenuId(menuId, PageRequest.of(0, 10));

        // Assert
        Assert.assertEquals(2, result.getTotalElements());
    }
}
