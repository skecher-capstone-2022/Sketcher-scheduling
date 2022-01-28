package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {


}
