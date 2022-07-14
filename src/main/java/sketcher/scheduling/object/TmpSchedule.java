package sketcher.scheduling.object;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class TmpSchedule {
    private final String cellId;
    private final LocalDate date;
//    private final Day day;
    private final int time;   //0~23
    private TmpManager tmpManager;  //매개변수 전달을 줄이기 위해 필드 변수로 올림

    @Builder
    public TmpSchedule(String cellId, LocalDate date, int time, TmpManager tmpManager) {
        this.cellId = cellId;
        this.date = date;
        this.time = time;
        this.tmpManager = tmpManager;
    }

    public TmpSchedule toEntity(){
        return TmpSchedule.builder()
                .cellId(cellId)
                .date(date)
                .time(time)
                .tmpManager(tmpManager)
                .build();
    }

    public void updateTmpManager(TmpManager updateManager){
        tmpManager = updateManager;
    }



}
