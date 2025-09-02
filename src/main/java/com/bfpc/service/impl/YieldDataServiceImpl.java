package com.bfpc.service.impl;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.YieldData;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.repository.FarmerRepository;
import com.bfpc.repository.YieldDataRepository;
import com.bfpc.service.YieldDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the YieldDataService interface.
 */
@Service
@RequiredArgsConstructor
public class YieldDataServiceImpl implements YieldDataService {

    private final YieldDataRepository yieldDataRepository;
    private final FarmerRepository farmerRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<YieldData> getAllYieldData(Pageable pageable) {
        return yieldDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public YieldData getYieldDataById(Long id) {
        return yieldDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yield data not found with id: " + id));
    }

    @Override
    @Transactional
    public YieldData createYieldData(YieldData yieldData) {
        // Calculate yield per hectare if not provided
        if (yieldData.getYieldPerHectare() == null && yieldData.getYieldQuantity() != null && yieldData.getAreaPlantedInHectares() != null) {
            double yieldPerHectare = yieldData.getYieldQuantity() / yieldData.getAreaPlantedInHectares();
            yieldData.setYieldPerHectare(yieldPerHectare);
        }
        return yieldDataRepository.save(yieldData);
    }

    @Override
    @Transactional
    public YieldData updateYieldData(Long id, YieldData yieldData) {
        YieldData existingYieldData = yieldDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yield data not found with id: " + id));

        // Update fields
        existingYieldData.setCropType(yieldData.getCropType());
        existingYieldData.setHarvestDate(yieldData.getHarvestDate());
        existingYieldData.setPlantingDate(yieldData.getPlantingDate());
        existingYieldData.setAreaPlantedInHectares(yieldData.getAreaPlantedInHectares());
        existingYieldData.setYieldQuantity(yieldData.getYieldQuantity());
        existingYieldData.setYieldUnit(yieldData.getYieldUnit());
        
        // Calculate yield per hectare if not provided
        if (yieldData.getYieldPerHectare() != null) {
            existingYieldData.setYieldPerHectare(yieldData.getYieldPerHectare());
        } else if (yieldData.getYieldQuantity() != null && yieldData.getAreaPlantedInHectares() != null) {
            double yieldPerHectare = yieldData.getYieldQuantity() / yieldData.getAreaPlantedInHectares();
            existingYieldData.setYieldPerHectare(yieldPerHectare);
        }
        
        existingYieldData.setSoilType(yieldData.getSoilType());
        existingYieldData.setIrrigationUsed(yieldData.getIrrigationUsed());
        existingYieldData.setFertilizerUsed(yieldData.getFertilizerUsed());
        existingYieldData.setPesticideUsed(yieldData.getPesticideUsed());
        existingYieldData.setSeedVariety(yieldData.getSeedVariety());
        existingYieldData.setWeatherConditions(yieldData.getWeatherConditions());
        existingYieldData.setChallengesFaced(yieldData.getChallengesFaced());
        existingYieldData.setTrainingImplemented(yieldData.getTrainingImplemented());
        existingYieldData.setPercentageChangeFromPreviousYield(yieldData.getPercentageChangeFromPreviousYield());

        return yieldDataRepository.save(existingYieldData);
    }

    @Override
    @Transactional
    public void deleteYieldData(Long id) {
        if (!yieldDataRepository.existsById(id)) {
            throw new ResourceNotFoundException("Yield data not found with id: " + id);
        }
        yieldDataRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<YieldData> getYieldDataByFarmerId(Long farmerId, Pageable pageable) {
        return yieldDataRepository.findByFarmerId(farmerId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<YieldData> getYieldDataByCropType(String cropType, Pageable pageable) {
        try {
            YieldData.CropType type = YieldData.CropType.valueOf(cropType.toUpperCase());
            return yieldDataRepository.findByCropType(type, pageable);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid crop type: " + cropType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<YieldData> getYieldDataByHarvestDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return yieldDataRepository.findByHarvestDateBetween(startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<YieldData> getTopYieldDataByYieldPerHectare(int limit) {
        return yieldDataRepository.findTopByYieldPerHectare(PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageYieldPerHectareByCropType(String cropType) {
        try {
            YieldData.CropType type = YieldData.CropType.valueOf(cropType.toUpperCase());
            return yieldDataRepository.findAverageYieldPerHectareByCropType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid crop type: " + cropType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<YieldData> getYieldDataByFarmerAndCropType(Long farmerId, String cropType, Pageable pageable) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + farmerId));
        
        try {
            YieldData.CropType type = YieldData.CropType.valueOf(cropType.toUpperCase());
            return yieldDataRepository.findByFarmerAndCropType(farmer, type, pageable);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid crop type: " + cropType);
        }
    }
}