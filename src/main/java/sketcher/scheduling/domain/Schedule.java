package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;
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
public class Schedule extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "schedule_time")
    private Integer scheduleTime;

    private Integer workforce;

    private Integer expected_card_cnt;

    private String creator_id;
    private String update_id;

    @OneToMany(mappedBy = "schedule")
    private List<ManagerWorkingSchedule> managerWorkingScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<ManagerAssignSchedule> managerAssignSchedules = new ArrayList<>();

    @Builder
    public Schedule(Date scheduleDate) {
    this.scheduleDate = scheduleDate;
    }

    protected Schedule() {
    }
}
