package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;

import java.util.List;
import java.util.Optional;

public interface ScheduleUpdateReqRepository extends JpaRepository<ScheduleUpdateReq,Integer>{
    List<ScheduleUpdateReq> findAll();
    Optional<ScheduleUpdateReq> findById(Integer id);
}
