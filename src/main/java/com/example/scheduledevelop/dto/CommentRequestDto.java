package com.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "contents 는 필수값입니다.")
    private final String contents;
    private final Long scheduleId;

}
