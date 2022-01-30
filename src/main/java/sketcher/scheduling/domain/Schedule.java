package sketcher.scheduling.domain;

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

public class Schedule extends ScheduleTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "schedule_time")
    private Integer scheduleTime;

    private Integer workforce;

    private Integer expected_card_cnt;

    /**
     * 현재 스케줄 DB 테스트중.. 나중에 유저정보 받고 넣어보기
     */
//    @NotEmpty
//    private String creator_id;
//
//    @NotEmpty
//    private String update_id;


    /**
     * 연관관계 매핑
     */
>>>>>>> a1b345011df227365952b78d5097406501159c5b

    @OneToMany(mappedBy = "schedule")
    private List<ManagerWorkingSchedule> managerWorkingScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
<<<<<<< HEAD
    private List<ManagerAssignSchedule> managerAssignSchedules = new ArrayList<>();

    @Builder
    public Schedule(Integer schedule_date, Integer schedule_time, Integer workforce, Integer expected_card_cnt) {
        this.schedule_date = schedule_date;
        this.schedule_time = schedule_time;
=======
    private List<ManagerAssignSchedule> managerAssignScheduleList = new ArrayList<>();

    /**
     * Builder 에 관한 설명은 /domain/User에!
     */
    @Builder
    public Schedule(Date scheduleDate, Integer scheduleTime, Integer workforce, Integer expected_card_cnt) {
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
>>>>>>> a1b345011df227365952b78d5097406501159c5b
        this.workforce = workforce;
        this.expected_card_cnt = expected_card_cnt;
    }

<<<<<<< HEAD
=======


>>>>>>> a1b345011df227365952b78d5097406501159c5b
    protected Schedule() {
    }
}
