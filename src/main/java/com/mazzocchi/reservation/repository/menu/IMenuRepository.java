package com.mazzocchi.reservation.repository.menu;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IMenuRepository extends JpaRepository<Menu, Long> {
}
