package com.mazzocchi.reservation.service.impl.v1;

import com.mazzocchi.reservation.config.exception.*;
import com.mazzocchi.reservation.dto.menu.*;
import com.mazzocchi.reservation.mapper.*;
import com.mazzocchi.reservation.models.*;
import com.mazzocchi.reservation.repository.menu.*;
import com.mazzocchi.reservation.service.interfaces.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Service
public class DinnerServiceV1Impl implements IDinnerService {
    final private IDinnerRepository dinnerRepository;

    private final IDinnerMapper dinnerMapper = IDinnerMapper.INSTANCE;

    public DinnerServiceV1Impl(IDinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    @Override
    public Page<DinnerDto> findAllDinnersByMenuId(Long menuId, Pageable pageable) {
        return dinnerRepository.findDinnersByMenuId(menuId, pageable).map(dinnerMapper::dinnerToDto);
    }

    @Override
    public DinnerDto findDinnerByIdAndMenuId(Long dinnerId, Long menuId) {
        final Dinner dinners = dinnerRepository.findDinnerByIdAndMenuId(dinnerId, menuId);

        if (dinners == null) {
            throw new NotFoundException("Dinner not found with id: " + dinnerId + " and menuId: " + menuId);
        }

        return dinnerMapper.dinnerToDto(dinners);
    }

    @Override
    public DinnerDto findDinnerById(Long dinnerId) {
        final Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> new NotFoundException("Dinner not found with id: " + dinnerId));
        return dinnerMapper.dinnerToDto(dinner);
    }

}
