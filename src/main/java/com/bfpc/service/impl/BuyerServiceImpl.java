package com.bfpc.service.impl;

import com.bfpc.domain.entity.Buyer;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.BuyerDto;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.mapper.BuyerMapper;
import com.bfpc.repository.BuyerRepository;
import com.bfpc.repository.UserRepository;
import com.bfpc.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the BuyerService interface.
 */
@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;
    private final BuyerMapper buyerMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerDto> getAllBuyers(Pageable pageable) {
        return buyerRepository.findAll(pageable)
                .map(buyerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerDto getBuyerById(Long id) {
        return buyerRepository.findById(id)
                .map(buyerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerDto getBuyerByUserId(Long userId) {
        return buyerRepository.findByUserId(userId)
                .map(buyerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with user id: " + userId));
    }

    @Override
    @Transactional
    public BuyerDto updateBuyer(Long id, BuyerDto buyerDto) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id: " + id));

        buyerMapper.updateBuyerFromDto(buyerDto, buyer);
        return buyerMapper.toDto(buyerRepository.save(buyer));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerDto> getBuyersByVerificationStatus(boolean verified, Pageable pageable) {
        return buyerRepository.findByVerified(verified, pageable)
                .map(buyerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerDto> getBuyersByCropInterest(String cropType, Pageable pageable) {
        try {
            Buyer.CropType type = Buyer.CropType.valueOf(cropType.toUpperCase());
            return buyerRepository.findByCropInterestsContaining(type, pageable)
                    .map(buyerMapper::toDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid crop type: " + cropType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerDto> getTopBuyersByRating(int limit) {
        return buyerRepository.findTopBuyersByAverageRating(limit).stream()
                .map(buyerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerDto> getTopBuyersByTransactions(int limit) {
        return buyerRepository.findTopBuyersBySuccessfulTransactions(limit).stream()
                .map(buyerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBuyerOwner(Long buyerId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        return buyerRepository.findById(buyerId)
                .map(buyer -> buyer.getUser().getId().equals(user.getId()))
                .orElse(false);
    }
}