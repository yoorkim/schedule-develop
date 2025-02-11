package com.example.scheduledevelop.controller;

import com.example.scheduledevelop.dto.*;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@Valid @RequestBody ScheduleRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        return new ResponseEntity<>(scheduleService.save(requestDto, loggedInMemberDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedResponseDto<ScheduleDetailDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(scheduleService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto requestDto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        return new ResponseEntity<>(scheduleService.update(id, requestDto, loggedInMemberDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        scheduleService.delete(id, loggedInMemberDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
