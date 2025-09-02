package com.bfpc.service;

import com.bfpc.dto.MessageDto;
import com.bfpc.dto.CommunityDto;

import java.util.List;

public interface ChatService {
    List<CommunityDto> getAllCommunities();
    List<CommunityDto> getUserCommunities(String userId);
    void joinCommunity(String communityId, String userId);
    void leaveCommunity(String communityId, String userId);
    List<MessageDto> getMessages(String chatId);
    MessageDto sendMessage(MessageDto messageDto);
    String startDirectChat(String userId, String participantId);
}