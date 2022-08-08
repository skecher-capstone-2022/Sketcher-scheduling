package sketcher.scheduling.algorithm;

import org.springframework.stereotype.Component;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.util.ArrayList;

@Component
public class AutoScheduling {
    public static final int TIME_LENGTH = 24;

    private final ManagerHopeTimeService managerHopeTimeService;
    private final EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository;

    // 생성자 직접 주입
    public AutoScheduling(ManagerHopeTimeService managerHopeTimeService, EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository) {
        this.managerHopeTimeService = managerHopeTimeService;
        this.estimatedNumOfCardsPerHourRepository = estimatedNumOfCardsPerHourRepository;
    }

    public ArrayList<ResultScheduling> runAlgorithm(int userCode[], int userCurrentTime[]) {

        //1. SETUP  변수값 저장
        // - 가중치 설정

        //2. 4가지 조건 고려 (가중치 점수 합산하는 함수를 작성)
        //(1) 매니저 가중치 (M1, M2, M3) - MANAGER클래스 내부에 함수 작성
        //(2) 매니저 현재 배정시간 - MANAGER클래스 내부에 함수 작성
        //(3) 매니저 희망시간 개수 (1,2,3,4) - MANAGER클래스 내부에 함수 작성 (고정)
        //(4) 이전 시간 배정 여부 (되면..)


        /* CYCLE START */
        //3. B타임 - 시간대별 필요인원 계산 -> 스케줄 노드 생성
        // 스케줄 노드 값 설정(고정 매니저 포함 여부, 스케줄 가중치)

        //4. 이분매칭(dfs)
        // 스케줄 노드에 배정된 매니저 코드 저장

        /* CYCLE FINISH */

        //5. C타임 스케줄링 배정 사이클

        //6. D타임 스케줄링 배정 사이클

        //7. A타임 스케줄링 배정 사이클






        /*RETURN*/
//        ArrayList<ResultScheduling> schedulingsResults = new ArrayList<>(); // 타입 지정
//        for (int i = 0; i < TIME_LENGTH; i++) {
//            for (int j = 0; j < /*같은 시간대에 배정되는 매니저 수*/; j++) {
//                schedulingsResults.add(new ResultScheduling(/*startTime*/,/*userCode*/, /*currentTime*/);
//            }
//        }

        return schedulingsResults;
    }

}
