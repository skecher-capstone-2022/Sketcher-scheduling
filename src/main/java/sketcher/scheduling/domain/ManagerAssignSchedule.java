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

    @NotEmpty
    private Character schedule_delete_req_check;

    /**
     * 연관관계 매핑
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerAssignSchedule(Integer id, Character schedule_delete_req_check, User user, Schedule schedule) {
        this.id = id;
        this.schedule_delete_req_check = schedule_delete_req_check;

        if(this.user.getManagerAssignSchedules() != null){
            user.getManagerAssignSchedules().remove(this);
        }
        this.user = user;

        if(this.schedule.getManagerAssignScheduleList() != null){
            schedule.getManagerAssignScheduleList().remove(this);
        }
        this.schedule = schedule;
    }

    protected ManagerAssignSchedule(){}
}
