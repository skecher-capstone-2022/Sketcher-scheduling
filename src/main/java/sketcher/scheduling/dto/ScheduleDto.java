package sketcher.scheduling.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sketcher.scheduling.domain.Schedule;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ScheduleDto {

    private Integer id;
    private Date scheduleDate;
//    private Integer scheduleTime;
//    private Integer workforce;
//    private Integer expected_card_cnt;
//    private String creator_id;
//    private String update_id;

    @Builder
    public ScheduleDto(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Schedule toEntity(){
        return Schedule.builder()
                .scheduleDate(scheduleDate)
                .build();
    }
}
