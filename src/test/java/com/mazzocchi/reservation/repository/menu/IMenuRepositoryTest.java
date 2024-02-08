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
public class IMenuRepositoryTest {
    @Autowired
    private IMenuRepository iMenuRepository;

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

        entityManager.persist(menu);
        entityManager.flush();

        // Act
        Page<Menu> result = iMenuRepository.findByState(state, PageRequest.of(0, 10));

        // Assert
        Assert.assertEquals(3, result.getTotalElements());
    }

}
