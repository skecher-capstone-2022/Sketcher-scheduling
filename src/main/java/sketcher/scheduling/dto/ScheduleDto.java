package sketcher.scheduling.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;
import sketcher.scheduling.domain.Schedule;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(value = "스케줄")
public class ScheduleDto {


    private Integer id;

    private Date scheduleDate;

    private Integer scheduleTime;

    private Integer workforce;

    private Integer expected_card_cnt;

//    private String creator_id;
//    private String update_id;

    /**
     * 스케줄에는 creator_id 로 user_id 값이 입력 됨.
     * 그렇다면 user 에서는 ?
     *
     */
    @Builder
    public ScheduleDto(Date scheduleDate, Integer scheduleTime, Integer workforce, Integer expected_card_cnt) {
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.workforce = workforce;
        this.expected_card_cnt = expected_card_cnt;
    }

    public Schedule toEntity(){
        return Schedule.builder()
                .scheduleDate(scheduleDate)
                .scheduleTime(scheduleTime)
                .workforce(workforce)
                .expected_card_cnt(expected_card_cnt)
                .build();
    }
}
