package sketcher.scheduling.exception;

public class NoExsistTmpScheduleException extends RuntimeException{
    public NoExsistTmpScheduleException(String cellId){
        super(cellId+"번에 해당하는 TmpSchedule객체가 존재하지 않습니다.");
    }
}
