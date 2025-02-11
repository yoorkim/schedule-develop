package com.example.scheduledevelop.repository;

import com.example.scheduledevelop.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist schedule id = " + id));
    }

    @Query("SELECT s, COUNT(c) FROM Schedule s LEFT JOIN Comment c ON c.schedule.id = s.id GROUP BY s")
    Page<Object[]> findAllWithCommentCount(Pageable pageable);

    void deleteByMemberId(Long memberId);
}
