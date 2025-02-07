package com.example.scheduledevelop.service;

import com.example.scheduledevelop.dto.CreateScheduleRequestDto;
import com.example.scheduledevelop.dto.ScheduleResponseDto;
import com.example.scheduledevelop.dto.UpdateScheduleRequestDto;
import com.example.scheduledevelop.entity.Schedule;
import com.example.scheduledevelop.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    @Transactional
    public ScheduleResponseDto save(CreateScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto.getMemberName(), requestDto.getTitle(), requestDto.getContents());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getMemberName(), savedSchedule.getTitle(), savedSchedule.getContents(),
                savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll()
    {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleResponseDto> dtoList= new ArrayList<>();

        for (Schedule schedule : scheduleList) {
            dtoList.add(new ScheduleResponseDto(schedule.getId(), schedule.getMemberName(), schedule.getTitle(), schedule.getContents(),
                    schedule.getCreatedAt(), schedule.getModifiedAt()));
        }

        return dtoList;
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getMemberName(), findSchedule.getTitle(), findSchedule.getContents(),
                findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    @Transactional
    public ScheduleResponseDto update(Long id, UpdateScheduleRequestDto requestDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        findSchedule.setTitle(requestDto.getTitle());
        findSchedule.setContents(requestDto.getContents());

        entityManager.flush();

        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getMemberName(), findSchedule.getTitle(), findSchedule.getContents(),
                findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        scheduleRepository.delete(findSchedule);
    }
}
