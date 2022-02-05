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


    @ApiParam(value = "스케줄 아이디", required = true, example = "1")
    private Integer id;
    @ApiParam(value = "스케줄 날짜", required = true, example = "2022/03/31")
    private Date scheduleDate;
    @ApiParam(value = "스케줄 시간", required = true, example = "1")
    private Integer scheduleTime;
    @ApiParam(value = "스케줄 인원", required = true, example = "1")
    private Integer workforce;
    @ApiParam(value = "예상 카드 건수", required = true, example = "1")
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
