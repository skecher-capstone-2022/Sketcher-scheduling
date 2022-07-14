//package sketcher.scheduling.object;
//
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Date;
//import java.util.List;
//
//@Getter
//public class TmpScheduleModel {
//    private final Date date;
//    private final Day day;
//    private final int time;   //0~23
//    private List<TmpManager> managers;
//
//    TmpManager tmpManager;  //매개변수 전달을 줄이기 위해 필드 변수로 올림
//
//    @Builder
//    public TmpScheduleModel(Date date, Day day, int time) {
//        this.date = date;
//        this.day = day;
//        this.time = time;
//    }
//
//    public TmpScheduleModel toEntity(){
//        return TmpScheduleModel.builder()
//                .date(date)
//                .day(day)
//                .time(time)
//                .build();
//    }
//
//    List<TmpManager> updateManagerList(TmpManager tmpManager){
//        this.tmpManager = tmpManager;
//
//        if(!isManagerExsist(tmpManager)){
//            addManager();
//        }
//        return managers;
//    }
//
//    boolean isManagerExsist(TmpManager findManager) {
//        for (TmpManager manager : managers) {
//            if(manager==findManager)
//                return true;
//        }
//        return false;
//    }
//
//    void addManager() {
//        managers.add(tmpManager);
//    }
//
//
//}
