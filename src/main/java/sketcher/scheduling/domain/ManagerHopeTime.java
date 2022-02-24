package sketcher.scheduling.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.Manager;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "manager_hope_time")
@Getter
public class ManagerHopeTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hope_time_id")
    private Integer id;

    private Integer start_time;

    private Integer finish_time;

    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerHopeTime(Integer start_time,Integer finish_time, User user) {
        this.start_time = start_time;
        this.finish_time = finish_time;
        if(this.user != null){
            this.user.getManagerHopeTimeList().remove(this);
        }
        this.user = user;
        user.getManagerHopeTimeList().add(this);
    }

    protected ManagerHopeTime(){

    }
}
