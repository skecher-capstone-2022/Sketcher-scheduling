package sketcher.scheduling.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.dto.ScheduleDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findAll();

    Optional<Schedule> findById(Integer id);

    Optional<Schedule> findByScheduleDateTimeStartAndScheduleDateTimeEnd(LocalDateTime startDate, LocalDateTime endDate);

    void deleteById(Integer Id);

}
