package com.example.scheduledevelop.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String memberName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public MemberResponseDto(Long id, String memberName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.memberName = memberName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
