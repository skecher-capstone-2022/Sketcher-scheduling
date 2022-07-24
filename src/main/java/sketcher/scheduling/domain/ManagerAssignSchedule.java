package sketcher.scheduling.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "manager_assign_schedule")
@Getter
public class ManagerAssignSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_schedule_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_req_id")
    private ScheduleUpdateReq updateReq;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    @Column(name = "schedule_date_time_start")
    private LocalDateTime scheduleDateTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    @Column(name = "schedule_date_time_end")
    private LocalDateTime scheduleDateTimeEnd;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerAssignSchedule(Integer id, User user, ScheduleUpdateReq updateReq, LocalDateTime scheduleDateTimeStart, LocalDateTime scheduleDateTimeEnd) {
        this.id = id;
        this.user = user;
        this.updateReq = updateReq;
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.scheduleDateTimeEnd = scheduleDateTimeEnd;

        if (this.user != null) {
            user.getManagerAssignScheduleList().remove(this);
        }
        this.user = user;
        user.getManagerAssignScheduleList().add(this);
    }

    public void update(LocalDateTime scheduleDateTimeStart, LocalDateTime scheduleDateTimeEnd){
        this.scheduleDateTimeStart = scheduleDateTimeStart;
        this.scheduleDateTimeEnd = scheduleDateTimeEnd;
    }

    public void addUpdateReq(ScheduleUpdateReq updatReq){
        this.updateReq = updatReq;
        updateReq.setAssignSchedule(this);
    }


    protected ManagerAssignSchedule() {
    }



}
