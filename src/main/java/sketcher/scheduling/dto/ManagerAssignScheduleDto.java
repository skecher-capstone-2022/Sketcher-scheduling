package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class ManagerAssignScheduleDto {

    private Integer id;
    private Character schedule_delete_req_check;
    private User user;
    private Schedule schedule;

    @Builder
    public ManagerAssignScheduleDto(Character schedule_delete_req_check, User user, Schedule schedule) {
        this.schedule_delete_req_check = schedule_delete_req_check;
        this.user = user;
        this.schedule = schedule;
    }

    public ManagerAssignSchedule toEntity(){
       return ManagerAssignSchedule.builder()
                .schedule_delete_req_check(schedule_delete_req_check)
                .user(user)
                .schedule(schedule)
                .build();
    }
}
