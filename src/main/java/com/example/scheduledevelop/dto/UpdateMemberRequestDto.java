package com.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class UpdateMemberRequestDto {

    private final String memberName;

    public UpdateMemberRequestDto(String memberName) {
        this.memberName = memberName;
    }
}
