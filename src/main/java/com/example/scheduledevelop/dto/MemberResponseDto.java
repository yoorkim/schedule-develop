package com.example.scheduledevelop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MemberResponseDto {

    private final Long id;
    private final String memberName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

}
