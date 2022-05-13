package sketcher.scheduling.object;

import lombok.Getter;

@Getter
public class TmpManager {
    private final int usercode;
    private final String userid;
    private final String username;
    private int[] hopeTime;
    private int totalTime = 0;
    private int weekendTime = 0;

    public void plusTime(boolean isWeekend){
        if(isWeekend)
            weekendTime++;
        totalTime++;
    }

    @lombok.Builder
    public TmpManager(int usercode, String userid, String username, int[] hopeTime) {
        this.usercode = usercode;
        this.userid = userid;
        this.username = username;
        this.hopeTime = hopeTime;
    }

    public TmpManager toEntity(){

        return TmpManager.builder()
                .usercode(usercode)
                .userid(userid)
                .username(username)
                .hopeTime(hopeTime)
                .build();
    }

}
