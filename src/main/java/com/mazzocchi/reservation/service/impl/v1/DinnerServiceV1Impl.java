package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Service
public class DinnerServiceV1Impl implements IDinnerService {
    final private IDinnerRepository dinnerRepository;

    public DinnerServiceV1Impl(IDinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    @Override
    public Page<Dinner> findAllDinnersByMenuId(Long menuId, Pageable pageable) {
        return dinnerRepository.findDinnersByMenuId(menuId, pageable);
    }

    @Override
    public Dinner findDinnerByIdAndMenuId(Long dinnerId, Long menuId) {
        return dinnerRepository.findDinnerByIdAndMenuId(dinnerId, menuId);
    }

    @Override
    public Dinner findDinnerById(Long dinnerId) {
        return dinnerRepository.findById(dinnerId).orElseThrow(() -> new NotFoundException("Dinner not found with id: " + dinnerId));
    }

}
