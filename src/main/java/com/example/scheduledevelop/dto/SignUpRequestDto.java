package com.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private final String memberName;
    private final String email;

    public SignUpRequestDto(String memberName, String email) {
        this.memberName = memberName;
        this.email = email;
    }
}
