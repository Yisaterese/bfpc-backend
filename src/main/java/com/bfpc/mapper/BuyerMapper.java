package com.bfpc.mapper;

import com.bfpc.domain.entity.Buyer;
import com.bfpc.dto.BuyerDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the Buyer entity.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BuyerMapper {

    /**
     * Convert a Buyer entity to a BuyerDto.
     *
     * @param buyer the Buyer entity
     * @return the BuyerDto
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "preferredLanguage", source = "user.preferredLanguage")
    @Mapping(target = "address", source = "user.address")
    @Mapping(target = "cropInterests", expression = "java(mapCropTypesToStrings(buyer.getCropInterests()))")
    BuyerDto toDto(Buyer buyer);

    /**
     * Convert a BuyerDto to a Buyer entity.
     *
     * @param buyerDto the BuyerDto
     * @return the Buyer entity
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cropInterests", expression = "java(mapStringsToCropTypes(buyerDto.getCropInterests()))")
    Buyer toEntity(BuyerDto buyerDto);

    /**
     * Update a Buyer entity with a BuyerDto.
     *
     * @param buyer the Buyer entity to update
     * @param buyerDto the BuyerDto with the new values
     * @return the updated Buyer entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cropInterests", expression = "java(mapStringsToCropTypes(buyerDto.getCropInterests()))")
    Buyer updateBuyerFromDto(BuyerDto buyerDto, @MappingTarget Buyer buyer);

    /**
     * Map crop types from enum to strings.
     *
     * @param cropTypes the set of crop types
     * @return the set of crop type strings
     */
    default Set<String> mapCropTypesToStrings(Set<Buyer.CropType> cropTypes) {
        if (cropTypes == null) {
            return null;
        }
        return cropTypes.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    /**
     * Map crop types from strings to enum.
     *
     * @param cropTypeStrings the set of crop type strings
     * @return the set of crop types
     */
    default Set<Buyer.CropType> mapStringsToCropTypes(Set<String> cropTypeStrings) {
        if (cropTypeStrings == null) {
            return null;
        }
        return cropTypeStrings.stream()
                .map(Buyer.CropType::valueOf)
                .collect(Collectors.toSet());
    }
}