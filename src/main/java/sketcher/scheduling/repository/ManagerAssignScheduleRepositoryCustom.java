package sketcher.scheduling.repository;

import sketcher.scheduling.domain.ManagerAssignSchedule;

import java.util.List;

public interface ManagerAssignScheduleRepositoryCustom {
    List<ManagerAssignSchedule> findByUserId(String id);
}
