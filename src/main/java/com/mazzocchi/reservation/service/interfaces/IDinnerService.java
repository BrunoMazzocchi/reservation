package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.dto.menu.*;
import org.springframework.data.domain.*;

public interface IDinnerService {
    Page<DinnerDto> findAllDinnersByMenuId(Long menuId, Pageable pageable);

    DinnerDto findDinnerByIdAndMenuId(Long dinnerId, Long menuId);

    DinnerDto findDinnerById(Long dinnerId);
}

