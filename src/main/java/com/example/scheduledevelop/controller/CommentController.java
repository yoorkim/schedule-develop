package com.example.scheduledevelop.controller;

import com.example.scheduledevelop.dto.CommentResponseDto;
import com.example.scheduledevelop.dto.CommentRequestDto;
import com.example.scheduledevelop.dto.LoggedInMemberDto;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> saveComment(@Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        return new ResponseEntity<>(commentService.save(requestDto, loggedInMemberDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequestDto requestDto,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        return new ResponseEntity<>(commentService.update(id, requestDto, loggedInMemberDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoggedInMemberDto loggedInMemberDto = new LoggedInMemberDto((Member) session.getAttribute("sessionKey"));

        commentService.delete(id, loggedInMemberDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
