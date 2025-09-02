package com.bfpc.mapper;

import com.bfpc.domain.entity.Transaction;
import com.bfpc.dto.TransactionDto;
import org.mapstruct.*;

/**
 * Mapper for the Transaction entity.
 */
@Mapper(componentModel = "spring", uses = {FarmerMapper.class, BuyerMapper.class})
public interface TransactionMapper {

    /**
     * Convert a Transaction entity to a TransactionDto.
     *
     * @param transaction the Transaction entity
     * @return the TransactionDto
     */
    @Mapping(target = "farmerId", source = "farmer.id")
    @Mapping(target = "farmerName", expression = "java(transaction.getFarmer().getUser().getFirstName() + ' ' + transaction.getFarmer().getUser().getLastName())")
    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "buyerName", expression = "java(transaction.getBuyer().getUser().getFirstName() + ' ' + transaction.getBuyer().getUser().getLastName())")
    @Mapping(target = "cropType", expression = "java(transaction.getCropType().name())")
    @Mapping(target = "status", expression = "java(transaction.getStatus().name())")
    TransactionDto toDto(Transaction transaction);

    /**
     * Convert a TransactionDto to a Transaction entity.
     *
     * @param transactionDto the TransactionDto
     * @return the Transaction entity
     */
    @Mapping(target = "farmer", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "cropType", expression = "java(Transaction.CropType.valueOf(transactionDto.getCropType()))")
    @Mapping(target = "status", expression = "java(Transaction.Status.valueOf(transactionDto.getStatus() != null ? transactionDto.getStatus() : \"INITIATED\"))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Transaction toEntity(TransactionDto transactionDto);

    /**
     * Update a Transaction entity with a TransactionDto.
     *
     * @param transaction the Transaction entity to update
     * @param transactionDto the TransactionDto with the new values
     * @return the updated Transaction entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farmer", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "cropType", expression = "java(transactionDto.getCropType() != null ? Transaction.CropType.valueOf(transactionDto.getCropType()) : transaction.getCropType())")
    @Mapping(target = "status", expression = "java(transactionDto.getStatus() != null ? Transaction.Status.valueOf(transactionDto.getStatus()) : transaction.getStatus())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Transaction updateTransactionFromDto(TransactionDto transactionDto, @MappingTarget Transaction transaction);
}