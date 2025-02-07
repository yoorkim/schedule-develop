package com.example.scheduledevelop.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final String memberName;
    private final String title;
    private final String contents;

    public CreateScheduleRequestDto(String memberName, String title, String contents) {
        this.memberName = memberName;
        this.title = title;
        this.contents = contents;
    }
}
