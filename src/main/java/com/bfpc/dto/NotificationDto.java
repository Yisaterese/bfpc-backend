package com.bfpc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String id;
    private String userId;
    private String title;
    private String message;
    private String type; // "info", "success", "warning", "error"
    private Boolean read;
    private LocalDateTime timestamp;
    private String actionUrl;
    private String icon;
}