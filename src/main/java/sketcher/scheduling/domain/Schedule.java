package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter
public class Schedule {

    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Integer id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer schedule_date;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer schedule_time;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer workforce;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer expected_card_cnt;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private LocalDateTime create_date;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private String creator_id;

    private String update_id;
    private LocalDateTime update_date;

    @OneToMany(mappedBy = "schedule")
    private List<ManagerWorkingSchedule> managerWorkingScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<ManagerAssignSchedule> managerAssignSchedules = new ArrayList<>();

    @Builder
    public Schedule(Integer schedule_date, Integer schedule_time, Integer workforce, Integer expected_card_cnt) {
        this.schedule_date = schedule_date;
        this.schedule_time = schedule_time;
        this.workforce = workforce;
        this.expected_card_cnt = expected_card_cnt;
    }

    protected Schedule() {
    }
}
