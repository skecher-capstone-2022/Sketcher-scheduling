package sketcher.scheduling.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_update_req")
@Getter
public class ScheduleUpdateReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_req_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "assign_schedule_id")
    private ManagerAssignSchedule assignSchedule;

    @Column(name = "req_accept_check")
    private Character reqAcceptCheck;

    @Column(name="change_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    private LocalDateTime changeDate;

    @Column(name="req_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    private LocalDateTime reqTime;

    @Builder
    public ScheduleUpdateReq(Integer id,ManagerAssignSchedule assignSchedule, Character reqAcceptCheck, LocalDateTime changeDate,LocalDateTime reqTime) {
        this.id = id;
        this.assignSchedule = assignSchedule;
        this.reqAcceptCheck = reqAcceptCheck;
        this.changeDate = changeDate;
        this.reqTime = reqTime;
    }

    public void update(ManagerAssignSchedule assignSchedule, LocalDateTime changeDate){
        this.assignSchedule = assignSchedule;
        this.changeDate = changeDate;
    }

    protected ScheduleUpdateReq(){}
}
