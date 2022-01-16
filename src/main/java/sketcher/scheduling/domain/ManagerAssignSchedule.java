package sketcher.scheduling.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_assign_schedule")
@Getter
public class ManagerAssignSchedule {

    @Id @GeneratedValue
    @Column(name = "assign_schedule_id")
    private Integer id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer schedule_id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private String manager_id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Character schedule_delete_req_check;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
