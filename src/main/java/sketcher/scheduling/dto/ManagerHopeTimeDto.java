package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerHopeTimeDto {

    private Integer id;
    private Integer start_time;
    private Integer finish_time;
    private User user;


    @Builder
    public ManagerHopeTimeDto(Integer start_time, Integer finish_time, User user) {
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.user = user;
    }


    public ManagerHopeTime toEntity() {
        return ManagerHopeTime.builder()
                .start_time(start_time)
                .finish_time(finish_time)
                .user(user)
                .build();
    }
}
