package sketcher.scheduling.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter

public class Schedule{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    @Column(name = "schedule_date_time_start")
    private LocalDateTime scheduleDateTimeStart;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
//    @Column(name = "schedule_date_time_end")
//    private LocalDateTime scheduleDateTimeEnd;


    private Integer workforce;

    private Integer expected_card_cnt;

    private String creator_id;

    private String update_id;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

//    /**
//     * 연관관계 매핑
//     */
//
//    @OneToMany(mappedBy = "schedule")
//    private List<ManagerAssignSchedule> managerAssignScheduleList = new ArrayList<>();

    /**
     * Builder 에 관한 설명은 /domain/User에!
     */
    @Builder
    public Schedule(LocalDateTime scheduleDateTimeStart, Integer workforce, Integer expected_card_cnt, String creator_id, String update_id, LocalDateTime create_date, LocalDateTime update_date) {
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.workforce = workforce;
        this.expected_card_cnt = expected_card_cnt;
        this.creator_id = creator_id;
        this.update_id = update_id;
        this.create_date = create_date;
        this.update_date = update_date;
    }


    protected Schedule() {
    }
}
