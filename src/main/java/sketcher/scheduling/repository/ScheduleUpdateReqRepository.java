package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;

import java.util.List;
import java.util.Optional;

public interface ScheduleUpdateReqRepository extends JpaRepository<ScheduleUpdateReq, Integer> {
    List<ScheduleUpdateReq> findAll();

    Optional<ScheduleUpdateReq> findById(Integer id);
    Integer findByAssignSchedule(ManagerAssignSchedule managerAssignSchedule);

    @Query("select r from ScheduleUpdateReq r where r.reqAcceptCheck='N'")
    List<ScheduleUpdateReq> updateReqResultList();

}
