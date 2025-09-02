package com.bfpc.service.impl;

import com.bfpc.dto.MessageDto;
import com.bfpc.dto.CommunityDto;
import com.bfpc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    // Mock data - in a real app, this would come from a database
    private final List<CommunityDto> communities = initializeCommunities();
    private final List<MessageDto> messages = new ArrayList<>();

    @Override
    public List<CommunityDto> getAllCommunities() {
        return new ArrayList<>(communities);
    }

    @Override
    public List<CommunityDto> getUserCommunities(String userId) {
        // In a real app, filter by user's joined communities
        return communities.stream()
                .filter(CommunityDto::getIsJoined)
                .toList();
    }

    @Override
    public void joinCommunity(String communityId, String userId) {
        communities.stream()
                .filter(c -> c.getId().equals(communityId))
                .findFirst()
                .ifPresent(community -> {
                    community.setIsJoined(true);
                    community.setMemberCount(community.getMemberCount() + 1);
                });
    }

    @Override
    public void leaveCommunity(String communityId, String userId) {
        communities.stream()
                .filter(c -> c.getId().equals(communityId))
                .findFirst()
                .ifPresent(community -> {
                    community.setIsJoined(false);
                    community.setMemberCount(Math.max(0, community.getMemberCount() - 1));
                });
    }

    @Override
    public List<MessageDto> getMessages(String chatId) {
        return messages.stream()
                .filter(m -> m.getChatId().equals(chatId))
                .toList();
    }

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        messageDto.setId(UUID.randomUUID().toString());
        messageDto.setTimestamp(LocalDateTime.now());
        messages.add(messageDto);
        
        // In a real app, you would trigger Pusher event here
        // pusher.trigger(channelName, "new-message", messageDto);
        
        return messageDto;
    }

    @Override
    public String startDirectChat(String userId, String participantId) {
        // Generate a unique chat ID
        String chatId = "direct-" + System.currentTimeMillis();
        return chatId;
    }

    private List<CommunityDto> initializeCommunities() {
        List<CommunityDto> initialCommunities = new ArrayList<>();
        
        initialCommunities.add(new CommunityDto(
                "comm-1", "Rice Farmers Makurdi", 
                "Community for rice farmers in Makurdi LGA to share experiences and best practices",
                "Makurdi LGA", 1247, "Rice Farming", "/images/rice-community.jpg", false
        ));
        
        initialCommunities.add(new CommunityDto(
                "comm-2", "Yam Growers Gboko",
                "Connect with fellow yam farmers in Gboko area for knowledge sharing",
                "Gboko LGA", 892, "Yam Farming", "/images/yam-community.jpg", true
        ));
        
        initialCommunities.add(new CommunityDto(
                "comm-3", "Cassava Processors Network",
                "Network of cassava farmers and processors across Benue State",
                "Benue State", 2156, "Cassava Processing", "/images/cassava-community.jpg", false
        ));
        
        return initialCommunities;
    }
}