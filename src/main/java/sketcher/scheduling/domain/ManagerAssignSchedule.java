package sketcher.scheduling.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_assign_schedule")
@Getter
public class ManagerAssignSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_schedule_id")
    private Integer id;

    @Column(name = "schedule_update_req_check")
    private Character scheduleUpdateReqCheck;

    /**
     * 연관관계 매핑
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerAssignSchedule(Integer id, Character scheduleUpdateReqCheck, User user, Schedule schedule) {
        this.id = id;
        this.scheduleUpdateReqCheck = scheduleUpdateReqCheck;

        if(this.user != null){
            user.getManagerAssignScheduleList().remove(this);
        }
        this.user = user;
        user.getManagerAssignScheduleList().add(this);

        if(this.schedule != null){
            schedule.getManagerAssignScheduleList().remove(this);
        }
        this.schedule = schedule;
        schedule.getManagerAssignScheduleList().add(this);
    }

    protected ManagerAssignSchedule(){}
}
