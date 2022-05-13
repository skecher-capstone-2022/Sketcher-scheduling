package sketcher.scheduling.exception;

public class NoAvailableManagerException extends RuntimeException{
    public NoAvailableManagerException(){
        super("해당 날짜에 근무 가능한 매니저가 없습니다.");
    }
}
