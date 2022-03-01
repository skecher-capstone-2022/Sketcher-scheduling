package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "schedule_update_req")
@Getter
public class ScheduleUpdateReq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "update_req_id")
    private Integer id;

    @Column(name = "assign_schedule_id")
    private Integer scheduleId;

    @Column(name = "req_accept_check")
    private Character reqAcceptCheck;

    @Builder
    public ScheduleUpdateReq(Integer id, Integer scheduleId, Character reqAcceptCheck) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.reqAcceptCheck = reqAcceptCheck;
    }

    protected ScheduleUpdateReq(){}
}
