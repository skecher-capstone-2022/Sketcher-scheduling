package sketcher.scheduling.algorithm;

import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.object.HopeTime;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;
import org.springframework.stereotype.Component;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final double FIXED_M3_RATIO = 3.0;
    List<Manager> managerList = new ArrayList<>();
    List<Schedule> scheduleListByDAWN = new ArrayList<>();
    List<Schedule> scheduleListByMORNING = new ArrayList<>();
    List<Schedule> scheduleListByAFTERNOON = new ArrayList<>();
    List<Schedule> scheduleListByEVENING = new ArrayList<>();
    double totalCardValueAvg;
    int numOfCreatedScheduleNode = 0;

    public ArrayList<ResultScheduling> runAlgorithm(int userCode[], int userCurrentTime[]) {
        List<EstimatedNumOfCardsPerHour> cards = estimatedNumOfCardsPerHourRepository.findAll();
        List<PercentageOfManagerWeights> percentage = percentageOfManagerWeightsRepository.findAll();
        LinkedHashMap<Integer, Manager> managerNodes = makeManagerNode(userCode, userCurrentTime);

        int totalCardValueSum = 0;
        for (EstimatedNumOfCardsPerHour card : cards) {
            totalCardValueSum+=card.getNumOfCards();
        }
        totalCardValueAvg = totalCardValueSum/cards.size();

        List<EstimatedNumOfCardsPerHour> cardsByDAWN = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByMORNING = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByAFTERNOON = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByEVENING = new ArrayList<>();


        for (EstimatedNumOfCardsPerHour card : cards) {
            if (card.getTime() >= 0 && card.getTime() > 6) {
                cardsByDAWN.add(card);
            } else if (card.getTime() >= 6 && card.getTime() > 12) {
                cardsByMORNING.add(card);
            } else if (card.getTime() >= 12 && card.getTime() > 18) {
                cardsByAFTERNOON.add(card);
            } else if (card.getTime() >= 18 && card.getTime() > 24) {
                cardsByEVENING.add(card);
            }
        }

        //1. SETUP  변수값 저장 makeManagerWeightAndHopeTime(managerNodes, HopeTime.DAWN, percentage);


        //2. 4가지 조건 고려 (가중치 점수 합산하는 함수를 작성)
        //(1) 매니저 가중치 (M1, M2, M3) - MANAGER클래스 내부에 함수 작성
        //(2) 매니저 현재 배정시간 - MANAGER클래스 내부에 함수 작성
        //(3) 매니저 희망시간 개수 (1,2,3,4) - MANAGER클래스 내부에 함수 작성 (고정)
        //(4) 이전 시간 배정 여부 (되면..)


        /* CYCLE START */
        //3. B타임 - 시간대별 필요인원 계산 -> 스케줄 노드 생성
        settingScheduleNodes(cardsByMORNING,scheduleListByDAWN);    // 스케줄 노드 값 설정(고정 매니저 포함 여부, 스케줄 가중치)
        //4. 이분매칭(dfs)
        bipartiteMatching(scheduleListByDAWN);   // 스케줄 노드에 배정된 매니저 코드 저장
        /* CYCLE FINISH */

        //5. C타임 스케줄링 배정 사이클
        settingScheduleNodes(cardsByAFTERNOON,scheduleListByAFTERNOON);
        bipartiteMatching(scheduleListByAFTERNOON);

        //6. D타임 스케줄링 배정 사이클
        settingScheduleNodes(cardsByEVENING,scheduleListByEVENING);
        bipartiteMatching(scheduleListByEVENING);

        //7. A타임 스케줄링 배정 사이클
        settingScheduleNodes(cardsByMORNING,scheduleListByMORNING);
        bipartiteMatching(scheduleListByMORNING);





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

    private void bipartiteMatching(List<Schedule> scheduleList) {
        int count=0;
        for (int i = 0; i < scheduleList.size(); i++) {
            if (firstDFS(scheduleList.get(i))) count++;   //매칭 개수
        }
    }

    public LinkedHashMap<Integer, Manager> makeManagerNode(int[] userCode, int[] userCurrentTime) {
        LinkedHashMap<Integer, Manager> managerNode = new LinkedHashMap<>();

        for (int i = 0; i < userCode.length; i++) {
            Manager manager = new Manager();
            manager.setCode(userCode[i]);
            manager.setTotalAssignTime(userCurrentTime[i]);

            managerNode.put(userCode[i], manager);
        }

        return managerNode;
    }

    public LinkedHashMap<Integer, Manager> makeManagerWeightAndHopeTime(LinkedHashMap<Integer, Manager> managerNodes,
                                                                        HopeTime hopeTime, List<PercentageOfManagerWeights> percentage) {
        List<Tuple> joinDateByHopeTime = userService.findJoinDateByHopeTime(hopeTime.getStart_time());

        int count = joinDateByHopeTime.size();
        Integer high = 50/*percentage.get(0).getId().getHigh()*/;
        Integer middle = 25/*percentage.get(0).getId().getMiddle()*/;

        long highManager = Math.round(count * high * 0.01);
        long middleManager = Math.round(count * middle * 0.01) + highManager;

        int i;
        for (i = 0; i < highManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);

            List<HopeTime> hopeTimeList = manager.getHopeTimeList();
            hopeTimeList.add(hopeTime);

            manager.setHopeTimeList(hopeTimeList);
            manager.setWeight(3);
        }

        for (; i < middleManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);

            List<HopeTime> hopeTimeList = manager.getHopeTimeList();
            hopeTimeList.add(hopeTime);

            manager.setHopeTimeList(hopeTimeList);
            manager.setWeight(2);
        }

        for (; i < count; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);

            List<HopeTime> hopeTimeList = manager.getHopeTimeList();
            hopeTimeList.add(hopeTime);

            manager.setHopeTimeList(hopeTimeList);
            manager.setWeight(1);
        }

        return managerNodes;
    }

    private void settingScheduleNodes(List<EstimatedNumOfCardsPerHour> cards, List<Schedule> scheduleList) {
//        Integer totalScheduleNodeSize = 0;

//        for (EstimatedNumOfCardsPerHour card : cards) {
//            totalScheduleNodeSize += card.getNumOfCards();
//        }

        boolean managerWeightFlag = false;
        int pointer = 0;    //scheduleSectionSize, weightType를 가리키는 포인터
        int weight=0;


        for (EstimatedNumOfCardsPerHour card : cards) {
            double numOfFixedManager = cards.get(pointer).getNumOfCards() * FIXED_M3_RATIO;
            for (int i = 0; i < Math.round(numOfFixedManager); i++) {
                managerWeightFlag = true;
            }
            if (card.getNumOfCards() < totalCardValueAvg / 2) {
                weight = 1;
            } else if (card.getNumOfCards() < totalCardValueAvg * 2) {
                weight = 2;
            } else {
                weight = 3;
            }
            numOfCreatedScheduleNode++;
            scheduleList.add(new Schedule(numOfCreatedScheduleNode, card.getTime(),weight, managerWeightFlag));
            managerWeightFlag = false;
        }
    }

    public boolean firstDFS(Schedule scheduleNode) {
        /* 매니저리스트 currentTime 오름차순 정렬 */
        Collections.sort(managerList);
        for (Manager manager : managerList) {
            Schedule alreadyExistingScheduleNode = manager.findScheduleByTime(scheduleNode.getTime());
            if (!manager.isContrainHopeTimes(scheduleNode.getTime())) { //조건1. 희망시간 포함 여부
                continue;
            }
            if (manager.getDayAssignTime() > 3) {   // 조건2. 하루 배정 시간이 3시간이 넘으면 제외
                continue;
            }
            if (alreadyExistingScheduleNode != null) {            // 조건3. 이미 해당 매니저가 동시간대에 배정되어 있음
                // 이미 배정된 매니저가 managerWeightFlag=false이고, 현재 스케줄이 managerWeightFlag=true인 경우
                if (checkSwapping(alreadyExistingScheduleNode, scheduleNode)) {
                    alreadyExistingScheduleNode.setManager(null);
                    manager.updateAssignScheduleList(alreadyExistingScheduleNode, scheduleNode);
                    scheduleNode.setManager(manager);
                    return true;    //s1, s2에 M3가 배정되는 경우 -->
                }
                continue;
            }
            //매니저가 해당 시간대에 아직 배정되지 않은 경우 (alreadyExistingScheduleNode == null인 경우)
            if (scheduleNode.isManagerWeightFlag() && manager.getWeight() != 3) {
                continue;                                   //조건4. managerWeightFlag가 true라면 매니저는 반드시 M3여야 함
            }
            if (alreadyExistingScheduleNode == null || firstDFS(alreadyExistingScheduleNode)) {
                manager.updateAssignScheduleList(alreadyExistingScheduleNode, scheduleNode);
                scheduleNode.setManager(manager);
                return true;
            }
//isPriorityScoreHigherThanExistingeManager
        }
        return false;
    }
    private boolean checkSwapping(Schedule alreadyExistingScheduleNode, Schedule scheduleNode) {
        if (alreadyExistingScheduleNode.isManagerWeightFlag() == false && scheduleNode.isManagerWeightFlag() == true)
            return true;
        return false;
    }
}

