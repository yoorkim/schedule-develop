package com.example.scheduledevelop.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponseDto<T> {
    private final int totalPages;
    private final long totalElements;
    private final int page;
    private final int size;
    private final List<T> content;

    public PagedResponseDto(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.content = page.getContent();
    }
}
