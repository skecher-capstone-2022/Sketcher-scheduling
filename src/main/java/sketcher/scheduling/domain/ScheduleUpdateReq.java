package sketcher.scheduling.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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

    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "updateReq")
    private ManagerAssignSchedule assignSchedule;

    @Column(name = "req_accept_check")
    private Character reqAcceptCheck;

    @Column(name = "change_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    private LocalDateTime changeStartDate;

    @Column(name = "change_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    private LocalDateTime changeEndDate;

    @Column(name = "req_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    private LocalDateTime reqTime;


    @Builder
    public ScheduleUpdateReq(Integer id, ManagerAssignSchedule assignSchedule, Character reqAcceptCheck, LocalDateTime changeStartDate, LocalDateTime changeEndDate, LocalDateTime reqTime) {
        this.id = id;
        this.assignSchedule = assignSchedule;
        this.reqAcceptCheck = reqAcceptCheck;
        this.changeStartDate = changeStartDate;
        this.changeEndDate = changeEndDate;
        this.reqTime = reqTime;
    }

    public ScheduleUpdateReq() {

    }


    public void update(ManagerAssignSchedule assignSchedule, LocalDateTime changeStartDate, LocalDateTime changeEndDate) {
        this.assignSchedule = assignSchedule;
        this.changeStartDate = changeStartDate;
        this.changeEndDate = changeEndDate;
    }

    public void updateReqAcceptCheckToY() {
        this.reqAcceptCheck = 'Y';
    }
}