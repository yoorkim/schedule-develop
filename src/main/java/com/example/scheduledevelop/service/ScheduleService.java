package com.example.scheduledevelop.service;

import com.example.scheduledevelop.dto.ScheduleRequestDto;
import com.example.scheduledevelop.dto.LoggedInMemberDto;
import com.example.scheduledevelop.dto.ScheduleResponseDto;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.entity.Schedule;
import com.example.scheduledevelop.repository.CommentRepository;
import com.example.scheduledevelop.repository.MemberRepository;
import com.example.scheduledevelop.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public ScheduleResponseDto save(ScheduleRequestDto requestDto, LoggedInMemberDto memberDto) {
        // 현재 로그인된 회원 정보
        Member findMember = memberRepository.findMemberByEmailOrElseThrow(memberDto.getEmail());

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), findMember);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), findMember.getMemberName(), findMember.getEmail(), savedSchedule.getTitle(), savedSchedule.getContents(),
                savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleResponseDto> dtoList= new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            dtoList.add(new ScheduleResponseDto(schedule.getId(), schedule.getMember().getMemberName(), schedule.getMember().getEmail(),
                    schedule.getTitle(), schedule.getContents(),
                    schedule.getCreatedAt(), schedule.getModifiedAt()));
        }

        return dtoList;
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getMember().getMemberName(), findSchedule.getMember().getEmail(),
                findSchedule.getTitle(), findSchedule.getContents(),
                findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto, LoggedInMemberDto memberDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 작성자가 로그인된 회원과 일치하는지 확인
        if (!findSchedule.getMember().getEmail().equals(memberDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 일정만 수정할 수 있습니다.");
        }

        findSchedule.setTitle(requestDto.getTitle());
        findSchedule.setContents(requestDto.getContents());

        entityManager.flush();

        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getMember().getMemberName(), findSchedule.getMember().getEmail(),
                findSchedule.getTitle(), findSchedule.getContents(),
                findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Transactional
    public void delete(Long id, LoggedInMemberDto memberDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 작성자가 로그인된 회원과 일치하는지 확인
        if (!findSchedule.getMember().getEmail().equals(memberDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 일정만 삭제할 수 있습니다.");
        }

        // 관련된 댓글 삭제
        commentRepository.deleteByScheduleId(id);

        scheduleRepository.delete(findSchedule);
    }
}
