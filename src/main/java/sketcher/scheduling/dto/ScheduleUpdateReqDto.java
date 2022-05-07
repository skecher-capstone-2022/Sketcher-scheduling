package sketcher.scheduling.dto;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ScheduleUpdateReqDto {
    private Integer id;
    private ManagerAssignSchedule assignSchedule;
    private Character reqAcceptCheck;
    private LocalDateTime changeDate;
    private LocalDateTime reqTime;

    @Builder
    public ScheduleUpdateReqDto(Integer id, ManagerAssignSchedule assignSchedule, Character reqAcceptCheck, LocalDateTime changeDate,LocalDateTime reqTime) {
        this.id = id;
        this.assignSchedule = assignSchedule;
        this.reqAcceptCheck = reqAcceptCheck;
        this.changeDate = changeDate;
        this.reqTime = reqTime;
    }


    public ScheduleUpdateReq toEntity(){
        return ScheduleUpdateReq.builder()
                .id(id)
                .assignSchedule(assignSchedule)
                .reqAcceptCheck(reqAcceptCheck)
                .changeDate(changeDate)
                .reqTime(reqTime)
                .build();
    }
    public void update(ManagerAssignSchedule assignSchedule, LocalDateTime changeDate){
        this.assignSchedule = assignSchedule;
        this.changeDate = changeDate;
    }

}
