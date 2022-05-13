package sketcher.scheduling.service.createSchedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ManagerAssignScheduleRepositoryCustomImpl;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerAssignScheduleService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FromTmpToAssginScheduleService {
    private final CreateTmpScheduleService tmpScheduleService;
//    private final ManagerAssignScheduleService assignScheduleService;


    //스케줄 전체 저장
    public void saveAssignSchedule() {
        List<TmpSchedule> tmpScheduleList = tmpScheduleService.getTmpScheduleList();
        for (TmpSchedule tmpSchedule : tmpScheduleList) {


            ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                    .user(tmpSchedule.getTmpManager())
                    .scheduleDateTimeStart(startDate)
                    .scheduleDateTimeEnd(endDate)
                    .build();

            managerAssignScheduleService.saveManagerAssignSchedule(managerAssignScheduleDto);
        }


    }


}
