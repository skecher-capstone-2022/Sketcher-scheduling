package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.ManagerWorkingSchedule;

public interface ManagerWorkingScheduleRepository extends JpaRepository<ManagerWorkingSchedule, Integer> {

}
