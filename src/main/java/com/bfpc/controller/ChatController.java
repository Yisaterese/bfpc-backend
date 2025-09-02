package com.bfpc.controller;

import com.bfpc.dto.MessageDto;
import com.bfpc.dto.CommunityDto;
import com.bfpc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/communities")
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        return ResponseEntity.ok(chatService.getAllCommunities());
    }

    @GetMapping("/communities/{userId}")
    public ResponseEntity<List<CommunityDto>> getUserCommunities(@PathVariable String userId) {
        return ResponseEntity.ok(chatService.getUserCommunities(userId));
    }

    @PostMapping("/communities/{communityId}/join")
    public ResponseEntity<Void> joinCommunity(@PathVariable String communityId, @RequestParam String userId) {
        chatService.joinCommunity(communityId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/communities/{communityId}/leave")
    public ResponseEntity<Void> leaveCommunity(@PathVariable String communityId, @RequestParam String userId) {
        chatService.leaveCommunity(communityId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String chatId) {
        return ResponseEntity.ok(chatService.getMessages(chatId));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(chatService.sendMessage(messageDto));
    }

    @PostMapping("/direct-chat")
    public ResponseEntity<String> startDirectChat(@RequestParam String userId, @RequestParam String participantId) {
        return ResponseEntity.ok(chatService.startDirectChat(userId, participantId));
    }
}