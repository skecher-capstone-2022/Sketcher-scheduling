package sketcher.scheduling.exception;

//사용자의 아이디를 기반으로 데이터가 조회하지 않았을 경우 처리해주기 위한 Exception 클래스
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userid){
        super(userid + " NotFoundException");
    }
}
