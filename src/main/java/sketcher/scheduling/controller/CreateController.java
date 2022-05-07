package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.ScheduleService;
import sketcher.scheduling.service.UserService;

@Controller
@RequiredArgsConstructor
public class CreateController {

    private final UserService userService;
    private final ManagerHopeTimeService hopeTimeService;
    private final ScheduleService scheduleService;
    private final ManagerAssignScheduleService assignScheduleService;



    private  TmpManager tmpManager;
    private final TmpSchedule tmpSchedule;




}
