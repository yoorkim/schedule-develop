package com.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "title 은 필수값입니다.")
    @Size(max = 20, message = "title 은 최대 20자까지 입력 가능합니다.")
    private final String title;
    private final String contents;

}
