package com.example.scheduledevelop.dto;

import com.example.scheduledevelop.entity.Comment;
import com.example.scheduledevelop.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {
    private final Long id;
    private final String memberName;
    private final String email;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<Long> commentIds; // 댓글 ID 리스트

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.memberName = schedule.getMember().getMemberName();
        this.email = schedule.getMember().getEmail();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.commentIds = schedule.getComments().stream().map(Comment::getId).collect(Collectors.toList());
    }
}
