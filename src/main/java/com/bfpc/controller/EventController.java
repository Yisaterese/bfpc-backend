package com.bfpc.controller;

import com.bfpc.dto.EventDto;
import com.bfpc.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        return ResponseEntity.ok(eventService.createEvent(eventDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable String id, @RequestBody EventDto eventDto) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<Void> registerForEvent(@PathVariable String eventId, @RequestParam String userId) {
        eventService.registerForEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{eventId}/unregister")
    public ResponseEntity<Void> unregisterFromEvent(@PathVariable String eventId, @RequestParam String userId) {
        eventService.unregisterFromEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }
}