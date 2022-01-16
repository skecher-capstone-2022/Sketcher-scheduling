package sketcher.scheduling.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_working_schedule")
@Getter
public class ManagerWorkingSchedule {

    @Id @GeneratedValue
    @Column(name = "working_id")
    private Integer id;
    private Integer completed_card_cnt;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer schedule_id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private String manager_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
