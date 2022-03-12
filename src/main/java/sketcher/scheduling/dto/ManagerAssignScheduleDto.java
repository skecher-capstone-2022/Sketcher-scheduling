package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ManagerAssignScheduleDto {

    private Integer id;
    private User user;
    private Schedule schedule;
    private ScheduleUpdateReq updateReq;
    private LocalDateTime scheduleDateTimeStart;
    private LocalDateTime scheduleDateTimeEnd;


    @Builder
    public ManagerAssignScheduleDto(Integer id, User user, ScheduleUpdateReq updateReq, LocalDateTime scheduleDateTimeStart, LocalDateTime scheduleDateTimeEnd) {
        this.id = id;
        this.user = user;
        this.updateReq = updateReq;
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.scheduleDateTimeEnd = scheduleDateTimeEnd;
    }



    public ManagerAssignSchedule toEntity() {
        return ManagerAssignSchedule.builder()
                .id(id)
                .user(user)
                .updateReq(updateReq)
                .scheduleDateTimeStart(scheduleDateTimeStart)
                .scheduleDateTimeEnd(scheduleDateTimeEnd)
                .build();
    }
}
