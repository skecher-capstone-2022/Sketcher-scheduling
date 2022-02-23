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

//    private LocalTime start_time;
//
//    private LocalTime finish_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    @Column(name = "manager_hope_date_start")
        private LocalDateTime managerHopeDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+9")
    @Column(name = "manager_hope_date_end")
    private LocalDateTime managerHopeDateEnd;
    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 편의 메소드
     */
    @Builder
    public ManagerHopeTime(LocalDateTime managerHopeDateStart,LocalDateTime managerHopeDateEnd, User user) {
//        this.start_time = start_time;
//        this.finish_time = finish_time;
        this.managerHopeDateStart = managerHopeDateStart;
        this.managerHopeDateEnd = managerHopeDateEnd;
        if(this.user != null){
            this.user.getManagerHopeTimeList().remove(this);
        }
        this.user = user;
        user.getManagerHopeTimeList().add(this);
    }

    protected ManagerHopeTime(){

    }
}
