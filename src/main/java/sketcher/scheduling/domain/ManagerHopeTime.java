package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.Manager;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "manager_hope_time")
@Getter
public class ManagerHopeTime {

    @Id @GeneratedValue
    @Column(name = "hope_time_id")
    private Integer id;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer start_time;
    @NotEmpty(message = "Null 일 수 없습니다.")
    private Integer finish_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "Null 일 수 없습니다.")
    private User user;

    @Builder
    public ManagerHopeTime(Integer start_time, Integer finish_time) {
        this.start_time = start_time;
        this.finish_time = finish_time;
    }
    protected ManagerHopeTime(){

    }
}
