package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class ManagerAssignScheduleDto {

    private Integer id;
    private Character scheduleUpdateReqCheck;
    private User user;
    private Schedule schedule;

    @Builder
    public ManagerAssignScheduleDto(Character scheduleUpdateReqCheck, User user, Schedule schedule) {
        this.scheduleUpdateReqCheck = scheduleUpdateReqCheck;
        this.user = user;
        this.schedule = schedule;
    }

    public ManagerAssignSchedule toEntity(){
       return ManagerAssignSchedule.builder()
                .scheduleUpdateReqCheck(scheduleUpdateReqCheck)
                .user(user)
                .schedule(schedule)
                .build();
    }
}
