package com.bfpc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {
    private String id;
    private String name;
    private String description;
    private String location;
    private Integer memberCount;
    private String category;
    private String image;
    private Boolean isJoined;
}