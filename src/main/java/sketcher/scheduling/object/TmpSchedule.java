package sketcher.scheduling.object;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TmpSchedule {
    Date date;

    @Enumerated(EnumType.STRING)
    private Day day;

    int time;   //0~23

    List<TmpManager> managers;


    public TmpSchedule(Date date, Day day, int time) {
        this.date = date;
        this.day = day;
        this.time = time;
        managers = new ArrayList<>();
    }

    public void findIncludedManagers(int time){

    }
}
