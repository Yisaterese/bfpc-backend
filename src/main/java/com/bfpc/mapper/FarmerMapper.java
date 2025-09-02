package com.bfpc.mapper;

import com.bfpc.domain.entity.Cooperative;
import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.FarmerDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the Farmer entity.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FarmerMapper {

    /**
     * Convert a Farmer entity to a FarmerDto.
     *
     * @param farmer the Farmer entity
     * @return the FarmerDto
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "preferredLanguage", source = "user.preferredLanguage")
    @Mapping(target = "address", source = "user.address")
    @Mapping(target = "cropTypes", expression = "java(mapCropTypesToStrings(farmer.getCropTypes()))")
    @Mapping(target = "cooperativeIds", expression = "java(mapCooperativesToIds(farmer.getCooperatives()))")
    FarmerDto toDto(Farmer farmer);

    /**
     * Convert a FarmerDto to a Farmer entity.
     *
     * @param farmerDto the FarmerDto
     * @return the Farmer entity
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cropTypes", expression = "java(mapStringsToCropTypes(farmerDto.getCropTypes()))")
    @Mapping(target = "cooperatives", ignore = true)
    Farmer toEntity(FarmerDto farmerDto);

    /**
     * Update a Farmer entity with a FarmerDto.
     *
     * @param farmer the Farmer entity to update
     * @param farmerDto the FarmerDto with the new values
     * @return the updated Farmer entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cropTypes", expression = "java(mapStringsToCropTypes(farmerDto.getCropTypes()))")
    @Mapping(target = "cooperatives", ignore = true)
    Farmer updateFarmerFromDto(FarmerDto farmerDto, @MappingTarget Farmer farmer);

    /**
     * Map crop types from enum to strings.
     *
     * @param cropTypes the set of crop types
     * @return the set of crop type strings
     */
    default Set<String> mapCropTypesToStrings(Set<Farmer.CropType> cropTypes) {
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
    default Set<Farmer.CropType> mapStringsToCropTypes(Set<String> cropTypeStrings) {
        if (cropTypeStrings == null) {
            return null;
        }
        return cropTypeStrings.stream()
                .map(Farmer.CropType::valueOf)
                .collect(Collectors.toSet());
    }

    /**
     * Map cooperatives to their IDs.
     *
     * @param cooperatives the set of cooperatives
     * @return the set of cooperative IDs
     */
    default Set<Long> mapCooperativesToIds(Set<Cooperative> cooperatives) {
        if (cooperatives == null) {
            return null;
        }
        return cooperatives.stream()
                .map(Cooperative::getId)
                .collect(Collectors.toSet());
    }
}