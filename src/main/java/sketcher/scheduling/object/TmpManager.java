package sketcher.scheduling.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmpManager {
    String userid;
    String username;
    int total_time;
    int weekend_time;

    public TmpManager(String userid, String username) {
        this.userid = userid;
        this.username = username;
        this.total_time = 0;
        this.weekend_time = 0;
    }


}
