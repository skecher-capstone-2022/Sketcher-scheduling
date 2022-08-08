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
    private LocalDateTime changeStartDate;
    private LocalDateTime changeEndDate;
    private LocalDateTime reqTime;

    @Builder
    public ScheduleUpdateReqDto(ManagerAssignSchedule assignSchedule, Character reqAcceptCheck, LocalDateTime changeStartDate,LocalDateTime changeEndDate, LocalDateTime reqTime) {
        this.assignSchedule = assignSchedule;
        this.reqAcceptCheck = reqAcceptCheck;
        this.changeStartDate = changeStartDate;
        this.changeEndDate = changeEndDate;
        this.reqTime = reqTime;
    }


    public ScheduleUpdateReq toEntity(){
        return ScheduleUpdateReq.builder()
                .assignSchedule(assignSchedule)
                .reqAcceptCheck(reqAcceptCheck)
                .changeStartDate(changeStartDate)
                .changeEndDate(changeEndDate)
                .reqTime(reqTime)
                .build();
    }
    //TODO 재요청
//    public void update(ManagerAssignSchedule assignSchedule, LocalDateTime changeDate){
////        this.assignSchedule = assignSchedule;
//        this.changeDate = changeDate;
//
//    }

}