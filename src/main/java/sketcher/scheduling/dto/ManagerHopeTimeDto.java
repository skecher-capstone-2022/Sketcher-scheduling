package sketcher.scheduling.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerHopeTimeDto {

    private Integer id;
    private LocalTime start_time;
    private LocalTime finish_time;
    private LocalDateTime managerHopeDateStart;
    private LocalDateTime managerHopeDateEnd;
    private User user;

//    LocalTime start_time, LocalTime finish_time,
    @Builder
    public ManagerHopeTimeDto(LocalDateTime managerHopeDateStart,LocalDateTime managerHopeDateEnd, User user) {
        this.managerHopeDateStart = managerHopeDateStart;
        this.managerHopeDateEnd = managerHopeDateEnd;
        this.user = user;
    }


    public ManagerHopeTime toEntity() {
        return ManagerHopeTime.builder()
//                .start_time(start_time)
//                .finish_time(finish_time)
                .managerHopeDateStart(managerHopeDateStart)
                .managerHopeDateEnd(managerHopeDateEnd)
                .user(user)
                .build();
    }
}
