package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerWorkingSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class ManagerWorkingScheduleDto {

    private Integer id;
    private Integer completed_card_cnt;
    private User user;
    private Schedule schedule;

    @Builder
    public ManagerWorkingScheduleDto(Integer completed_card_cnt, User user, Schedule schedule) {
        this.completed_card_cnt = completed_card_cnt;
        this.user = user;
        this.schedule = schedule;
    }

    public ManagerWorkingSchedule toEntity(){
       return ManagerWorkingSchedule.builder()
                .completed_card_cnt(completed_card_cnt)
                .user(user)
                .schedule(schedule)
                .build();
    }
}
