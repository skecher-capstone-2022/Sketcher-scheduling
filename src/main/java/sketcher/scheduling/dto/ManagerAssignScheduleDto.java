package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ManagerAssignScheduleDto {

    private Integer id;
    private User user;
    private Schedule schedule;
    private ScheduleUpdateReq updateReq;


    @Builder
    public ManagerAssignScheduleDto(Integer id, User user, Schedule schedule, ScheduleUpdateReq updateReq) {
        this.id = id;
        this.user = user;
        this.schedule = schedule;
        this.updateReq = updateReq;
    }

    public ManagerAssignSchedule toEntity() {
        return ManagerAssignSchedule.builder()
                .id(id)
                .user(user)
                .schedule(schedule)
                .updateReq(updateReq)
                .build();
    }
}
