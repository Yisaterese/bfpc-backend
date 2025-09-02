package com.bfpc.mapper;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.FarmerDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T02:34:58+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Amazon.com Inc.)"
)
@Component
public class FarmerMapperImpl implements FarmerMapper {

    @Override
    public FarmerDto toDto(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }

        FarmerDto.FarmerDtoBuilder farmerDto = FarmerDto.builder();

        farmerDto.userId( farmerUserId( farmer ) );
        farmerDto.firstName( farmerUserFirstName( farmer ) );
        farmerDto.lastName( farmerUserLastName( farmer ) );
        farmerDto.email( farmerUserEmail( farmer ) );
        farmerDto.phoneNumber( farmerUserPhoneNumber( farmer ) );
        farmerDto.preferredLanguage( farmerUserPreferredLanguage( farmer ) );
        farmerDto.address( farmerUserAddress( farmer ) );
        farmerDto.id( farmer.getId() );
        farmerDto.farmSizeInHectares( farmer.getFarmSizeInHectares() );
        farmerDto.soilType( farmer.getSoilType() );
        farmerDto.yearsOfExperience( farmer.getYearsOfExperience() );
        farmerDto.hasIrrigation( farmer.getHasIrrigation() );
        farmerDto.primaryMarket( farmer.getPrimaryMarket() );
        farmerDto.trainingSessionsAttended( farmer.getTrainingSessionsAttended() );
        farmerDto.successfulTransactions( farmer.getSuccessfulTransactions() );
        farmerDto.averageYieldPerHectare( farmer.getAverageYieldPerHectare() );

        farmerDto.cropTypes( mapCropTypesToStrings(farmer.getCropTypes()) );
        farmerDto.cooperativeIds( mapCooperativesToIds(farmer.getCooperatives()) );

        return farmerDto.build();
    }

    @Override
    public Farmer toEntity(FarmerDto farmerDto) {
        if ( farmerDto == null ) {
            return null;
        }

        Farmer.FarmerBuilder farmer = Farmer.builder();

        farmer.id( farmerDto.getId() );
        farmer.farmSizeInHectares( farmerDto.getFarmSizeInHectares() );
        farmer.soilType( farmerDto.getSoilType() );
        farmer.yearsOfExperience( farmerDto.getYearsOfExperience() );
        farmer.hasIrrigation( farmerDto.getHasIrrigation() );
        farmer.primaryMarket( farmerDto.getPrimaryMarket() );
        farmer.trainingSessionsAttended( farmerDto.getTrainingSessionsAttended() );
        farmer.successfulTransactions( farmerDto.getSuccessfulTransactions() );
        farmer.averageYieldPerHectare( farmerDto.getAverageYieldPerHectare() );

        farmer.cropTypes( mapStringsToCropTypes(farmerDto.getCropTypes()) );

        return farmer.build();
    }

    @Override
    public Farmer updateFarmerFromDto(FarmerDto farmerDto, Farmer farmer) {
        if ( farmerDto == null ) {
            return farmer;
        }

        farmer.setFarmSizeInHectares( farmerDto.getFarmSizeInHectares() );
        farmer.setSoilType( farmerDto.getSoilType() );
        farmer.setYearsOfExperience( farmerDto.getYearsOfExperience() );
        farmer.setHasIrrigation( farmerDto.getHasIrrigation() );
        farmer.setPrimaryMarket( farmerDto.getPrimaryMarket() );
        farmer.setTrainingSessionsAttended( farmerDto.getTrainingSessionsAttended() );
        farmer.setSuccessfulTransactions( farmerDto.getSuccessfulTransactions() );
        farmer.setAverageYieldPerHectare( farmerDto.getAverageYieldPerHectare() );

        farmer.setCropTypes( mapStringsToCropTypes(farmerDto.getCropTypes()) );

        return farmer;
    }

    private Long farmerUserId(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String farmerUserFirstName(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String farmerUserLastName(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String farmerUserEmail(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String farmerUserPhoneNumber(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String phoneNumber = user.getPhoneNumber();
        if ( phoneNumber == null ) {
            return null;
        }
        return phoneNumber;
    }

    private String farmerUserPreferredLanguage(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String preferredLanguage = user.getPreferredLanguage();
        if ( preferredLanguage == null ) {
            return null;
        }
        return preferredLanguage;
    }

    private String farmerUserAddress(Farmer farmer) {
        if ( farmer == null ) {
            return null;
        }
        User user = farmer.getUser();
        if ( user == null ) {
            return null;
        }
        String address = user.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }
}
