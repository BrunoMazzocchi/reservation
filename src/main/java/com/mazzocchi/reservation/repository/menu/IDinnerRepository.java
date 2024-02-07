package com.mazzocchi.reservation.repository.menu;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;


@Repository
public interface IDinnerRepository extends JpaRepository<Dinner, Long> {

    @Query("SELECT d FROM Dinner d JOIN d.menus m WHERE m.id = :menuId")
    Page<Dinner> findDinnersByMenuId(@Param("menuId") Long menuId, Pageable pageable);

    @Query("SELECT d FROM Dinner d JOIN d.menus m WHERE d.id = :dinnerId AND m.id = :menuId")
    Dinner findDinnerByIdAndMenuId(@Param("dinnerId") Long dinnerId, @Param("menuId") Long menuId);
}
