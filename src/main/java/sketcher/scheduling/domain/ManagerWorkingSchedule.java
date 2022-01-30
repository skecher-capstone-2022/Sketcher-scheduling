package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_working_schedule")
@Getter
public class ManagerWorkingSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_id")
    private Integer id;
    private Integer completed_card_cnt;

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
    public ManagerWorkingSchedule(Integer id, Integer completed_card_cnt, User user, Schedule schedule){
        this.id = id;
        this.completed_card_cnt = completed_card_cnt;

        if(user.getManagerWorkingSchedules() != null){
            user.getManagerWorkingSchedules().remove(this);
        }
        this.user = user;
        user.getManagerWorkingSchedules().add(this);

        if(schedule.getManagerWorkingScheduleList() != null){
            schedule.getManagerWorkingScheduleList().remove(this);
        }
        this.schedule = schedule;
        schedule.getManagerWorkingScheduleList().add(this);
    }

    protected ManagerWorkingSchedule(){}
}
