package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sketcher.scheduling.domain.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule , Integer> {
    List<Schedule> findAll();
    Optional<Schedule> findById(Integer id);
}
