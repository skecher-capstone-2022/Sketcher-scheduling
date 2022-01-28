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
    @NotEmpty
    private Integer schedule_date;
    @NotEmpty
    private Integer schedule_time;
    @NotEmpty
    private Integer workforce;
    @NotEmpty
    private Integer expected_card_cnt;
    @NotEmpty
    private LocalDateTime create_date;

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
