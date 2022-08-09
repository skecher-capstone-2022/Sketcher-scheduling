package sketcher.scheduling.algorithm;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.object.HopeTime;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;
import org.springframework.stereotype.Component;
import sketcher.scheduling.service.UserService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static sketcher.scheduling.domain.QUser.user;


@Component
@RequiredArgsConstructor
public class AutoScheduling {
    private final UserService userService;
    private final EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository;
    private final PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    public static final int TIME_LENGTH = 24;

    public ArrayList<ResultScheduling> runAlgorithm(int userCode[], int userCurrentTime[]) {
        List<EstimatedNumOfCardsPerHour> cards = estimatedNumOfCardsPerHourRepository.findAll();
        List<PercentageOfManagerWeights> percentage = percentageOfManagerWeightsRepository.findAll();

        LinkedHashMap<Integer, Manager> managerNodes = makeManagerNode(userCode, userCurrentTime);
        makeManagerWeight(managerNodes, HopeTime.DAWN, percentage);

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

        return null;
//        return schedulingsResults;

    }

    public LinkedHashMap<Integer, Manager> makeManagerNode(int[] userCode, int[] userCurrentTime) {
        LinkedHashMap<Integer, Manager> managerNode = new LinkedHashMap<>();

        for (int i = 0; i < userCode.length; i++) {
            Manager manager = new Manager();
            manager.setCode(userCode[i]);
            manager.setTotalAssignTime(userCurrentTime[i]);

            List<HopeTime> hopeTimeList = manager.getHopeTimeList();
            hopeTimeList.add(HopeTime.EVENING);

            manager.setHopeTimeList(hopeTimeList);
            managerNode.put(userCode[i], manager);
        }

        return managerNode;
    }

    public LinkedHashMap<Integer, Manager> makeManagerWeight(LinkedHashMap<Integer, Manager> managerNodes,
                                                             HopeTime hopeTime, List<PercentageOfManagerWeights> percentage) {
        List<Tuple> joinDateByHopeTime = userService.findJoinDateByHopeTime(hopeTime.getStart_time());

        int count = joinDateByHopeTime.size();
        Integer high = percentage.get(0).getId().getHigh();
        Integer middle = percentage.get(0).getId().getMiddle();

        long highManager = Math.round(count * high * 0.01);
        long middleManager = Math.round(count * middle * 0.01) + highManager;
        long lowManager = count;

        int i;
        for (i = 0; i < highManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(3);
        }

        for (; i < middleManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(2);
        }

        for (; i < lowManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(1);
        }

        return managerNodes;
    }

}

