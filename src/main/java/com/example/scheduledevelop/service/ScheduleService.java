package com.example.scheduledevelop.service;

import com.example.scheduledevelop.dto.*;
import com.example.scheduledevelop.entity.Member;
import com.example.scheduledevelop.entity.Schedule;
import com.example.scheduledevelop.repository.MemberRepository;
import com.example.scheduledevelop.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public ScheduleResponseDto save(ScheduleRequestDto requestDto, LoggedInMemberDto memberDto) {
        // 현재 로그인된 회원 정보
        Member findMember = memberRepository.findMemberByEmailOrElseThrow(memberDto.getEmail());

        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), findMember);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public PagedResponseDto<ScheduleDetailDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("modifiedAt")));
        Page<Object[]> schedulePage = scheduleRepository.findAllWithCommentCount(pageable);

        Page<ScheduleDetailDto> responsePage = schedulePage.map(row -> {
            Schedule schedule = (Schedule) row[0];
            Long commentCount = (Long) row[1];

            return new ScheduleDetailDto(schedule, commentCount);
        });

        return new PagedResponseDto<>(responsePage);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(findSchedule);
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

        return new ScheduleResponseDto(findSchedule);
    }

    @Transactional
    public void delete(Long id, LoggedInMemberDto memberDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 일정 작성자가 로그인된 회원과 일치하는지 확인
        if (!findSchedule.getMember().getEmail().equals(memberDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 일정만 삭제할 수 있습니다.");
        }

        scheduleRepository.delete(findSchedule);
    }
}
