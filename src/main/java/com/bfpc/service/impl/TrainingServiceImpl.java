package com.bfpc.service.impl;

import com.bfpc.domain.entity.Farmer;
import com.bfpc.domain.entity.Training;
import com.bfpc.domain.entity.User;
import com.bfpc.dto.TrainingDto;
import com.bfpc.dto.UserDto;
import com.bfpc.exception.ResourceNotFoundException;
import com.bfpc.repository.FarmerRepository;
import com.bfpc.repository.TrainingRepository;
import com.bfpc.repository.UserRepository;
import com.bfpc.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the TrainingService interface.
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    @Override
    public Page<TrainingDto> getAllTrainings(Pageable pageable) {
        return trainingRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public TrainingDto getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + id));
    }

    @Override
    @Transactional
    public TrainingDto createTraining(TrainingDto trainingDto) {
        Training training = convertToEntity(trainingDto);
        return convertToDto(trainingRepository.save(training));
    }

    @Override
    @Transactional
    public TrainingDto updateTraining(Long id, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + id));

        // Update fields
        existingTraining.setTitle(trainingDto.getTitle());
        existingTraining.setDescription(trainingDto.getDescription());
        existingTraining.setStartDateTime(trainingDto.getStartDateTime());
        existingTraining.setEndDateTime(trainingDto.getEndDateTime());
        existingTraining.setLocation(trainingDto.getLocation());
        existingTraining.setGpsCoordinates(trainingDto.getGpsCoordinates());
        existingTraining.setOrganizer(trainingDto.getOrganizer());
        existingTraining.setFacilitator(trainingDto.getFacilitator());
        existingTraining.setContactPerson(trainingDto.getContactPerson());
        existingTraining.setContactPhone(trainingDto.getContactPhone());
        existingTraining.setCapacity(trainingDto.getCapacity());
        existingTraining.setIsActive(trainingDto.getIsActive());
        existingTraining.setTrainingMaterials(trainingDto.getTrainingMaterials());

        // Update crop focus
        if (trainingDto.getCropFocus() != null) {
            Set<Farmer.CropType> cropFocusSet = trainingDto.getCropFocus().stream()
                    .map(cropType -> Farmer.CropType.valueOf(cropType))
                    .collect(Collectors.toSet());
            existingTraining.setCropFocus(cropFocusSet);
        }

        return convertToDto(trainingRepository.save(existingTraining));
    }

    @Override
    @Transactional
    public void deleteTraining(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Training not found with id: " + id);
        }
        trainingRepository.deleteById(id);
    }

    @Override
    public Page<TrainingDto> getTrainingsByCategory(String category, Pageable pageable) {
        try {
            Farmer.CropType cropType = Farmer.CropType.valueOf(category.toUpperCase());
            return trainingRepository.findByCropFocus(cropType, pageable)
                    .map(this::convertToDto);
        } catch (IllegalArgumentException e) {
            // If the category is not a valid CropType, return an empty page
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<TrainingDto> getTrainingsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return trainingRepository.findByStartDateTimeBetween(startDateTime, endDateTime, pageable)
                .map(this::convertToDto);
    }

    @Override
    public Page<TrainingDto> getTrainingsByLocation(String location, Pageable pageable) {
        return trainingRepository.findByLocationIgnoreCase(location, pageable)
                .map(this::convertToDto);
    }

    @Override
    @Transactional
    public TrainingDto registerFarmerForTraining(Long trainingId, Long farmerId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + trainingId));

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + farmerId));

        // Check if training is at capacity
        if (training.getCapacity() != null && training.getAttendees().size() >= training.getCapacity()) {
            throw new IllegalStateException("Training is at full capacity");
        }

        // Check if farmer is already registered
        if (training.getAttendees().contains(farmer)) {
            throw new IllegalStateException("Farmer is already registered for this training");
        }

        // Register farmer
        training.getAttendees().add(farmer);

        // Update farmer's training sessions attended count
        farmer.setTrainingSessionsAttended(farmer.getTrainingSessionsAttended() + 1);
        farmerRepository.save(farmer);

        return convertToDto(trainingRepository.save(training));
    }

    @Override
    @Transactional
    public TrainingDto cancelTrainingRegistration(Long trainingId, Long farmerId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + trainingId));

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + farmerId));

        // Check if farmer is registered
        if (!training.getAttendees().contains(farmer)) {
            throw new IllegalStateException("Farmer is not registered for this training");
        }

        // Cancel registration
        training.getAttendees().remove(farmer);

        // Update farmer's training sessions attended count
        if (farmer.getTrainingSessionsAttended() > 0) {
            farmer.setTrainingSessionsAttended(farmer.getTrainingSessionsAttended() - 1);
            farmerRepository.save(farmer);
        }

        return convertToDto(trainingRepository.save(training));
    }

    @Override
    public Page<UserDto> getRegisteredFarmers(Long trainingId, Pageable pageable) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found with id: " + trainingId));

        // Get all farmers registered for this training
        List<Farmer> attendees = new ArrayList<>(training.getAttendees());

        // Convert to UserDto and paginate manually
        List<UserDto> userDtos = attendees.stream()
                .map(farmer -> {
                    User user = userRepository.findById(farmer.getUser().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found for farmer"));
                    return UserDto.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .role(user.getRole().name())
                            .build();
                })
                .collect(Collectors.toList());

        // Manual pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userDtos.size());
        List<UserDto> pageContent = userDtos.subList(start, end);

        return new org.springframework.data.domain.PageImpl<>(pageContent, pageable, userDtos.size());
    }

    @Override
    public Page<TrainingDto> getFarmerTrainings(Long farmerId, Pageable pageable) {
        if (!farmerRepository.existsById(farmerId)) {
            throw new ResourceNotFoundException("Farmer not found with id: " + farmerId);
        }

        return trainingRepository.findByAttendeeId(farmerId, pageable)
                .map(this::convertToDto);
    }

    /**
     * Convert a Training entity to a TrainingDto.
     *
     * @param training the Training entity
     * @return the TrainingDto
     */
    private TrainingDto convertToDto(Training training) {
        List<Long> attendeeIds = training.getAttendees().stream()
                .map(farmer -> farmer.getId())
                .collect(Collectors.toList());

        Set<String> cropFocusStrings = training.getCropFocus().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return TrainingDto.builder()
                .id(training.getId())
                .title(training.getTitle())
                .description(training.getDescription())
                .startDateTime(training.getStartDateTime())
                .endDateTime(training.getEndDateTime())
                .location(training.getLocation())
                .gpsCoordinates(training.getGpsCoordinates())
                .organizer(training.getOrganizer())
                .facilitator(training.getFacilitator())
                .contactPerson(training.getContactPerson())
                .contactPhone(training.getContactPhone())
                .capacity(training.getCapacity())
                .isActive(training.getIsActive())
                .trainingMaterials(training.getTrainingMaterials())
                .cropFocus(cropFocusStrings)
                .attendees(attendeeIds)
                .build();
    }

    /**
     * Convert a TrainingDto to a Training entity.
     *
     * @param trainingDto the TrainingDto
     * @return the Training entity
     */
    private Training convertToEntity(TrainingDto trainingDto) {
        Training training = new Training();
        training.setId(trainingDto.getId());
        training.setTitle(trainingDto.getTitle());
        training.setDescription(trainingDto.getDescription());
        training.setStartDateTime(trainingDto.getStartDateTime());
        training.setEndDateTime(trainingDto.getEndDateTime());
        training.setLocation(trainingDto.getLocation());
        training.setGpsCoordinates(trainingDto.getGpsCoordinates());
        training.setOrganizer(trainingDto.getOrganizer());
        training.setFacilitator(trainingDto.getFacilitator());
        training.setContactPerson(trainingDto.getContactPerson());
        training.setContactPhone(trainingDto.getContactPhone());
        training.setCapacity(trainingDto.getCapacity());
        training.setIsActive(trainingDto.getIsActive() != null ? trainingDto.getIsActive() : true);
        training.setTrainingMaterials(trainingDto.getTrainingMaterials());
        
        // Convert crop focus strings to enum values
        if (trainingDto.getCropFocus() != null && !trainingDto.getCropFocus().isEmpty()) {
            Set<Farmer.CropType> cropFocusSet = trainingDto.getCropFocus().stream()
                    .map(cropType -> Farmer.CropType.valueOf(cropType))
                    .collect(Collectors.toSet());
            training.setCropFocus(cropFocusSet);
        } else {
            training.setCropFocus(new HashSet<>());
        }

        // Handle attendees
        if (trainingDto.getAttendees() != null && !trainingDto.getAttendees().isEmpty()) {
            Set<Farmer> attendees = trainingDto.getAttendees().stream()
                    .map(farmerId -> farmerRepository.findById(farmerId)
                            .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id: " + farmerId)))
                    .collect(Collectors.toSet());
            training.setAttendees(attendees);
        } else {
            training.setAttendees(new HashSet<>());
        }

        return training;
    }
}