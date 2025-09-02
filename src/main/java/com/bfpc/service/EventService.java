package com.bfpc.service;

import com.bfpc.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getAllEvents();
    EventDto getEventById(String id);
    EventDto createEvent(EventDto eventDto);
    EventDto updateEvent(String id, EventDto eventDto);
    void deleteEvent(String id);
    void registerForEvent(String eventId, String userId);
    void unregisterFromEvent(String eventId, String userId);
}