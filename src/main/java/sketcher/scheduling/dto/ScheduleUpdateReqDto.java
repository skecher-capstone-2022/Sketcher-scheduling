package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ScheduleUpdateReq;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ScheduleUpdateReqDto {
    private Integer id;
    private Integer scheduleId;
    private Character reqAcceptCheck;

    @Builder
    public ScheduleUpdateReqDto(Integer id, Integer scheduleId, Character reqAcceptCheck) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.reqAcceptCheck = reqAcceptCheck;
    }

    public ScheduleUpdateReq toEntity(){
        return ScheduleUpdateReq.builder()
                .id(id)
                .scheduleId(scheduleId)
                .reqAcceptCheck(reqAcceptCheck)
                .build();
    }
}
