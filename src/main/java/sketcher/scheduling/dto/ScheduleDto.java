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

    private LocalDateTime scheduleDateTimeStart;

    private Integer workforce;

    private Integer expected_card_cnt;

    private String creator_id;
    private String update_id;

    private LocalDateTime create_date;

    private LocalDateTime update_date;
    /**
     * 스케줄에는 creator_id 로 user_id 값이 입력 됨.
     * 그렇다면 user 에서는 ?
     *
     */
    @Builder
    public ScheduleDto(LocalDateTime scheduleDateTimeStart, Integer workforce, Integer expected_card_cnt, String creator_id, String update_id, LocalDateTime create_date, LocalDateTime update_date) {
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.workforce = workforce;
        this.expected_card_cnt = expected_card_cnt;
        this.creator_id = creator_id;
        this.update_id = update_id;
        this.create_date = create_date;
        this.update_date = update_date;
    }

    public Schedule toEntity(){
        return Schedule.builder()
                .scheduleDateTimeStart(scheduleDateTimeStart)
                .workforce(workforce)
                .expected_card_cnt(expected_card_cnt)
                .creator_id(creator_id)
                .update_id(update_id)
                .create_date(create_date)
                .update_date(update_date)
                .build();
    }

    public void update(LocalDateTime scheduleDateTimeStart,LocalDateTime scheduleDateTimeEnd){
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.scheduleDateTimeEnd = scheduleDateTimeEnd;
    }
}
