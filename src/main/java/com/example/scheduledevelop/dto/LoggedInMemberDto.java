package com.example.scheduledevelop.dto;

import com.example.scheduledevelop.entity.Member;
import lombok.Getter;

@Getter
public class LoggedInMemberDto {
    private String email;

    public LoggedInMemberDto(Member member) {
        this.email = member.getEmail();
    }
}
