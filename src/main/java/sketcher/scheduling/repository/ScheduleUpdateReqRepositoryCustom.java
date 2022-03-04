package sketcher.scheduling.repository;
import sketcher.scheduling.domain.ScheduleUpdateReq;

import java.util.ArrayList;
import java.util.List;

public interface ScheduleUpdateReqRepositoryCustom {

    List<ScheduleUpdateReq> sort(String sort);
    ArrayList<String> findDetailById(String id);
}
