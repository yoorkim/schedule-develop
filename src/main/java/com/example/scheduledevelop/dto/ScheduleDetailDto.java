package com.example.scheduledevelop.dto;

import com.example.scheduledevelop.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ScheduleDetailDto {
    private Long id;
    private String title;
    private String contents;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String memberName;

    public ScheduleDetailDto(Schedule schedule, Long commentCount) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.commentCount = commentCount.intValue();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.memberName = schedule.getMember().getMemberName();
    }
}
