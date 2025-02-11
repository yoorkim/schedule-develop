package com.example.scheduledevelop.service;

import com.example.scheduledevelop.dto.CommentResponseDto;
import com.example.scheduledevelop.dto.CommentRequestDto;
import com.example.scheduledevelop.dto.LoggedInMemberDto;
import com.example.scheduledevelop.entity.Comment;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.entity.Schedule;
import com.example.scheduledevelop.repository.CommentRepository;
import com.example.scheduledevelop.repository.MemberRepository;
import com.example.scheduledevelop.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public CommentResponseDto save(@Valid CommentRequestDto requestDto, LoggedInMemberDto memberDto) {
        // 현재 로그인된 회원 정보
        Member findMember = memberRepository.findMemberByEmailOrElseThrow(memberDto.getEmail());
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(requestDto.getScheduleId());

        Comment comment = new Comment(requestDto.getContents(), findSchedule, findMember);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            dtoList.add(new CommentResponseDto(comment));
        }

        return dtoList;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        return new CommentResponseDto(findComment);
    }

    @Transactional
    public CommentResponseDto update(Long id, CommentRequestDto requestDto, LoggedInMemberDto memberDto) {
        // 현재 로그인된 회원 정보
        Member findMember = memberRepository.findMemberByEmailOrElseThrow(memberDto.getEmail());
        scheduleRepository.findByIdOrElseThrow(requestDto.getScheduleId());
        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        // 댓글 작성자가 로그인된 회원과 일치하는지 확인
        if (!findComment.getMember().getId().equals(findMember.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        if (!findComment.getSchedule().getId().equals(requestDto.getScheduleId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정 번호가 맞지 않습니다.");
        }

        findComment.setContents(requestDto.getContents());

        entityManager.flush();

        return new CommentResponseDto(findComment);
    }

    @Transactional
    public void delete(Long id, LoggedInMemberDto memberDto) {
        // 현재 로그인된 회원 정보
        Member findMember = memberRepository.findMemberByEmailOrElseThrow(memberDto.getEmail());
        Comment findComment = commentRepository.findByIdOrElseThrow(id);

        // 댓글 작성자가 로그인된 회원과 일치하는지 확인
        if (!findComment.getMember().getId().equals(findMember.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(findComment);
    }
}
