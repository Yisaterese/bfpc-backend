package com.bfpc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String id;
    private String senderId;
    private String senderName;
    private String senderAvatar;
    private String content;
    private LocalDateTime timestamp;
    private String type; // "text", "image", "file"
    private String chatId;
}