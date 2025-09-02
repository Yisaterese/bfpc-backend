package com.bfpc.service.impl;

import com.bfpc.dto.EventDto;
import com.bfpc.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final List<EventDto> events = initializeEvents();

    @Override
    public List<EventDto> getAllEvents() {
        return new ArrayList<>(events);
    }

    @Override
    public EventDto getEventById(String id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        eventDto.setId(UUID.randomUUID().toString());
        eventDto.setCurrentParticipants(0);
        events.add(eventDto);
        return eventDto;
    }

    @Override
    public EventDto updateEvent(String id, EventDto eventDto) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                eventDto.setId(id);
                events.set(i, eventDto);
                return eventDto;
            }
        }
        return null;
    }

    @Override
    public void deleteEvent(String id) {
        events.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public void registerForEvent(String eventId, String userId) {
        events.stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst()
                .ifPresent(event -> {
                    event.setRegistered(true);
                    event.setCurrentParticipants(event.getCurrentParticipants() + 1);
                });
    }

    @Override
    public void unregisterFromEvent(String eventId, String userId) {
        events.stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst()
                .ifPresent(event -> {
                    event.setRegistered(false);
                    event.setCurrentParticipants(Math.max(0, event.getCurrentParticipants() - 1));
                });
    }

    private List<EventDto> initializeEvents() {
        List<EventDto> initialEvents = new ArrayList<>();
        
        initialEvents.add(new EventDto(
                "event-1", "Modern Rice Farming Techniques",
                "Learn about the latest rice farming techniques and technologies",
                "Makurdi Agricultural Center", LocalDateTime.now().plusDays(7),
                "10:00 AM", "BFPC Training Team", "Training", "/images/rice-training.jpg",
                false, "upcoming", 50, 23
        ));
        
        initialEvents.add(new EventDto(
                "event-2", "Cassava Processing Workshop",
                "Hands-on workshop on cassava processing and value addition",
                "Gboko Processing Center", LocalDateTime.now().plusDays(14),
                "2:00 PM", "Cassava Processors Association", "Workshop", "/images/cassava-workshop.jpg",
                true, "upcoming", 30, 18
        ));
        
        return initialEvents;
    }
}