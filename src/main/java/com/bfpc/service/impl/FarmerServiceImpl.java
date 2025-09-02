package com.bfpc.service.impl;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.FarmerDto;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.mapper.FarmerMapper;
import com.bfpc.repository.FarmerRepository;
import com.bfpc.repository.UserRepository;
import com.bfpc.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the FarmerService interface.
 */
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final FarmerMapper farmerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerDto> getAllFarmers(Pageable pageable) {
        return farmerRepository.findAll(pageable)
                .map(farmerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerDto getFarmerById(Long id) {
        return farmerRepository.findById(id)
                .map(farmerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerDto getFarmerByUserId(Long userId) {
        return farmerRepository.findByUserId(userId)
                .map(farmerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with user id: " + userId));
    }

    @Override
    @Transactional
    public FarmerDto updateFarmer(Long id, FarmerDto farmerDto) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + id));

        farmerMapper.updateFarmerFromDto(farmerDto, farmer);
        return farmerMapper.toDto(farmerRepository.save(farmer));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerDto> getFarmersByCropType(String cropType, Pageable pageable) {
        try {
            Farmer.CropType type = Farmer.CropType.valueOf(cropType.toUpperCase());
            return farmerRepository.findByCropTypesContaining(type, pageable)
                    .map(farmerMapper::toDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid crop type: " + cropType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerDto> getFarmersByLocalGovernmentArea(String lga, Pageable pageable) {
        return farmerRepository.findByLocalGovernmentAreaIgnoreCase(lga, pageable)
                .map(farmerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmerDto> getTopFarmersByAverageYield(int limit) {
        return farmerRepository.findTopFarmersByAverageYieldPerHectare(limit).stream()
                .map(farmerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerDto> getSponsorshipEligibleFarmers(Pageable pageable) {
        return farmerRepository.findBySponsorshipEligibleTrue(pageable)
                .map(farmerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFarmerOwner(Long farmerId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        return farmerRepository.findById(farmerId)
                .map(farmer -> farmer.getUser().getId().equals(user.getId()))
                .orElse(false);
    }
}