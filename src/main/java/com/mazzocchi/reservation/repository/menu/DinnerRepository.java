package com.mazzocchi.reservation.repository.menu;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface DinnerRepository extends JpaRepository<Dinner, Long> {
}
