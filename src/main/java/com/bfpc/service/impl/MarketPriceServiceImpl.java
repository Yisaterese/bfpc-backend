package com.bfpc.service.impl;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.Market;
import com.bfpc.domain.entity.MarketPrice;
import com.bfpc.dto.MarketPriceDto;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.repository.MarketPriceRepository;
import com.bfpc.repository.MarketRepository;
import com.bfpc.service.MarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the MarketPriceService interface.
 */
@Service
@RequiredArgsConstructor
public class MarketPriceServiceImpl implements MarketPriceService {

    private final MarketPriceRepository marketPriceRepository;
    private final MarketRepository marketRepository;

    @Override
    public Page<MarketPriceDto> getAllMarketPrices(Pageable pageable) {
        return marketPriceRepository.findAllByOrderByDateDesc(pageable)
                .map(this::convertToDto);
    }

    @Override
    public MarketPriceDto getMarketPriceById(Long id) {
        return marketPriceRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Market price not found with id: " + id));
    }

    @Override
    @Transactional
    public MarketPriceDto createMarketPrice(MarketPriceDto marketPriceDto) {
        MarketPrice marketPrice = convertToEntity(marketPriceDto);
        return convertToDto(marketPriceRepository.save(marketPrice));
    }

    @Override
    @Transactional
    public MarketPriceDto updateMarketPrice(Long id, MarketPriceDto marketPriceDto) {
        MarketPrice existingMarketPrice = marketPriceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Market price not found with id: " + id));

        // Update fields
        existingMarketPrice.setCropType(Farmer.CropType.valueOf(marketPriceDto.getCropType()));
        existingMarketPrice.setUnit(marketPriceDto.getUnit());
        existingMarketPrice.setPrice(marketPriceDto.getPrice());
        existingMarketPrice.setLocation(marketPriceDto.getLocation());
        existingMarketPrice.setPriceDate(marketPriceDto.getPriceDate());
        existingMarketPrice.setQualityGrade(marketPriceDto.getQualityGrade());
        existingMarketPrice.setSource(marketPriceDto.getSource());
        existingMarketPrice.setPercentageChange(marketPriceDto.getPercentageChange());
        existingMarketPrice.setIsHighDemand(marketPriceDto.getIsHighDemand());

        return convertToDto(marketPriceRepository.save(existingMarketPrice));
    }

    @Override
    @Transactional
    public void deleteMarketPrice(Long id) {
        if (!marketPriceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Market price not found with id: " + id);
        }
        marketPriceRepository.deleteById(id);
    }

    @Override
    public Page<MarketPriceDto> getMarketPricesByCropType(String cropType, Pageable pageable) {
        return marketPriceRepository.findByCropTypeIgnoreCaseOrderByDateDesc(cropType, pageable)
                .map(this::convertToDto);
    }

    @Override
    public Page<MarketPriceDto> getMarketPricesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return marketPriceRepository.findByDateBetween(startDate, endDate, pageable)
                .map(this::convertToDto);
    }

    @Override
    public Page<MarketPriceDto> getMarketPricesByLocation(String location, Pageable pageable) {
        return marketPriceRepository.findByLocationIgnoreCase(location, pageable)
                .map(this::convertToDto);
    }

    @Override
    public Object getMarketPriceTrends(String cropType, Integer months) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minus(months, ChronoUnit.MONTHS);

        List<MarketPrice> marketPrices;
        if (cropType != null && !cropType.isEmpty()) {
            marketPrices = marketPriceRepository.findByCropTypeIgnoreCaseAndDateBetweenOrderByDateAsc(
                    cropType, startDate, endDate);
        } else {
            marketPrices = marketPriceRepository.findByDateBetween(startDate, endDate, Pageable.unpaged())
                    .getContent();
        }

        // Group by crop type and calculate trends
        Map<String, List<MarketPriceDto>> groupedByCropType = marketPrices.stream()
                .map(this::convertToDto)
                .collect(Collectors.groupingBy(MarketPriceDto::getCropType));

        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, List<MarketPriceDto>> entry : groupedByCropType.entrySet()) {
            String crop = entry.getKey();
            List<MarketPriceDto> prices = entry.getValue();

            // Sort by date
            prices.sort((p1, p2) -> p1.getDate().compareTo(p2.getDate()));

            // Calculate trend
            if (prices.size() >= 2) {
                MarketPriceDto oldest = prices.get(0);
                MarketPriceDto newest = prices.get(prices.size() - 1);

                if (newest.getPricePerKg().compareTo(oldest.getPricePerKg()) > 0) {
                    result.put(crop + "_trend", "up");
                } else if (newest.getPricePerKg().compareTo(oldest.getPricePerKg()) < 0) {
                    result.put(crop + "_trend", "down");
                } else {
                    result.put(crop + "_trend", "stable");
                }
            } else {
                result.put(crop + "_trend", "stable");
            }

            // Add prices data
            result.put(crop + "_prices", prices);
        }

        return result;
    }

    /**
     * Convert a MarketPrice entity to a MarketPriceDto.
     *
     * @param marketPrice the MarketPrice entity
     * @return the MarketPriceDto
     */
    private MarketPriceDto convertToDto(MarketPrice marketPrice) {
        MarketPriceDto dto = MarketPriceDto.builder()
                .id(marketPrice.getId())
                .cropType(marketPrice.getCropType().name())
                .unit(marketPrice.getUnit())
                .price(marketPrice.getPrice())
                .location(marketPrice.getLocation())
                .priceDate(marketPrice.getPriceDate())
                .qualityGrade(marketPrice.getQualityGrade())
                .source(marketPrice.getSource())
                .percentageChange(marketPrice.getPercentageChange())
                .isHighDemand(marketPrice.getIsHighDemand())
                .trend(marketPrice.getPercentageChange() != null ? 
                       (marketPrice.getPercentageChange().compareTo(BigDecimal.ZERO) > 0 ? "up" : 
                        marketPrice.getPercentageChange().compareTo(BigDecimal.ZERO) < 0 ? "down" : "stable") : null)
                .build();
                
        if (marketPrice.getMarket() != null) {
            dto.setMarketId(marketPrice.getMarket().getId());
            dto.setMarketName(marketPrice.getMarket().getName());
        }
        
        return dto;
    }

    /**
     * Convert a MarketPriceDto to a MarketPrice entity.
     *
     * @param marketPriceDto the MarketPriceDto
     * @return the MarketPrice entity
     */
    private MarketPrice convertToEntity(MarketPriceDto marketPriceDto) {
        MarketPrice marketPrice = MarketPrice.builder()
                .id(marketPriceDto.getId())
                .cropType(Farmer.CropType.valueOf(marketPriceDto.getCropType()))
                .unit(marketPriceDto.getUnit())
                .price(marketPriceDto.getPrice())
                .location(marketPriceDto.getLocation())
                .priceDate(marketPriceDto.getPriceDate())
                .qualityGrade(marketPriceDto.getQualityGrade())
                .source(marketPriceDto.getSource())
                .percentageChange(marketPriceDto.getPercentageChange())
                .isHighDemand(marketPriceDto.getIsHighDemand())
                .build();
        
        if (marketPriceDto.getMarketId() != null) {
            Market market = marketRepository.findById(marketPriceDto.getMarketId())
                    .orElseThrow(() -> new ResourceNotFoundException("Market not found with id: " + marketPriceDto.getMarketId()));
            marketPrice.setMarket(market);
        }
        
        return marketPrice;
    }
}