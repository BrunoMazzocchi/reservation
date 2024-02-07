package com.mazzocchi.reservation.service.interfaces;

import com.mazzocchi.reservation.models.*;
import org.springframework.data.domain.*;

public interface IDinnerService {
    Page<Dinner> findAllDinnersByMenuId(Long menuId, Pageable pageable);
    
    Dinner findDinnerByIdAndMenuId(Long dinnerId, Long menuId);

    Dinner findDinnerById(Long dinnerId);
}

