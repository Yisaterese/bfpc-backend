package com.bfpc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime date;
    private String time;
    private String organizer;
    private String category;
    private String image;
    private Boolean registered;
    private String status; // "upcoming", "ongoing", "completed"
    private Integer maxParticipants;
    private Integer currentParticipants;
}