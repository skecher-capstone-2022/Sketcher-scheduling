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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hope_time_id")
    private Integer id;
    @NotEmpty
    private Integer start_time;
    @NotEmpty
    private Integer finish_time;

    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotEmpty
    private User user;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerHopeTime(Integer start_time, Integer finish_time, User user) {
        this.start_time = start_time;
        this.finish_time = finish_time;
        if(this.user != null){
            this.user.getManagerHopeTimes().remove(this);
        }
        this.user = user;
        user.getManagerHopeTimes().add(this);
    }

    protected ManagerHopeTime(){

    }
}
