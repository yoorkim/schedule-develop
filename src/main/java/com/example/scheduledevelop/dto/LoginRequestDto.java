package com.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "email 은 필수값입니다.")
    private final String email;
    @NotBlank(message = "pwd 는 필수값입니다.")
    private final String pwd;

}
