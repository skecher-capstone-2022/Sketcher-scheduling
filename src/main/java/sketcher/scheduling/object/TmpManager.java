package sketcher.scheduling.object;

import lombok.Getter;
import sketcher.scheduling.domain.User;

@Getter
public class TmpManager {
    private final User user;
    private int[] hopeTime;
    private int totalTime = 0;
    private int weekendTime = 0;

    public void plusTime(boolean isWeekend){
        if(isWeekend)
            weekendTime++;
        totalTime++;
    }

    @lombok.Builder
    public TmpManager(User user, int[] hopeTime) {
        this.user = user;
        this.hopeTime = hopeTime;
    }

    public TmpManager toEntity(){

        return TmpManager.builder()
                .user(user)
                .hopeTime(hopeTime)
                .build();
    }

}
