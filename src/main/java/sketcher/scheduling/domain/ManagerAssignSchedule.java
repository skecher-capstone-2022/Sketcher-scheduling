package sketcher.scheduling.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_assign_schedule")
@Getter
public class ManagerAssignSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_schedule_id")
    private Integer id;

    /**
     * 연관관계 매핑
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    @OneToOne(mappedBy = "assignSchedule")
    private ScheduleUpdateReq updateReq;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerAssignSchedule(Integer id, User user, Schedule schedule,ScheduleUpdateReq updateReq) {
        this.id = id;
        this.updateReq = updateReq;

        if (this.user != null) {
            user.getManagerAssignScheduleList().remove(this);
        }
        this.user = user;
        user.getManagerAssignScheduleList().add(this);

        if (this.schedule != null) {
            schedule.getManagerAssignScheduleList().remove(this);
        }
        this.schedule = schedule;
        schedule.getManagerAssignScheduleList().add(this);
    }

    protected ManagerAssignSchedule() {
    }
}
