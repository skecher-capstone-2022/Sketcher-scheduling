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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaveAssginScheduleService {
    private final CreateTmpScheduleService tmpScheduleService;
    private final ManagerAssignScheduleService assignScheduleService;

    private List<TmpSchedule> tmpScheduleList;

    private void getTmpScheduleList() {
        tmpScheduleList = tmpScheduleService.getTmpScheduleList();
    }

    //스케줄 전체 저장
    public void saveAssignSchedule() {
        getTmpScheduleList();
        for (TmpSchedule tmpSchedule : tmpScheduleList) {
            ManagerAssignScheduleDto assignDto = createManagerAssignScheduleDto(tmpSchedule);
            assignScheduleService.saveManagerAssignSchedule(assignDto);
        }
        tmpScheduleService.reset();
    }

    private ManagerAssignScheduleDto createManagerAssignScheduleDto(TmpSchedule tmpSchedule) {
        LocalDateTime startDateTime = parserLocalDateTime(tmpSchedule, tmpSchedule.getTime());
        LocalDateTime endDateTime = parserLocalDateTime(tmpSchedule, tmpSchedule.getTime()+1);

        ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                .user(tmpSchedule.getTmpManager().getUser())
                .scheduleDateTimeStart(startDateTime)
                .scheduleDateTimeEnd(endDateTime)
                .build();
        return managerAssignScheduleDto;
    }

    private LocalDateTime parserLocalDateTime(TmpSchedule tmpSchedule, int time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDate date = tmpSchedule.getDate();
        String startDateTimeStr = date+" "+String.format("%02d", time)+":00:00.000";
        LocalDateTime dateTime = LocalDateTime.parse(startDateTimeStr, formatter);
        return dateTime;
    }


}
