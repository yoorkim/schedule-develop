package com.example.scheduledevelop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateMemberRequestDto {

    @NotBlank(message = "memberName 은 필수값입니다.")
    private final String memberName;
    @NotBlank(message = "email 은 필수값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

}
