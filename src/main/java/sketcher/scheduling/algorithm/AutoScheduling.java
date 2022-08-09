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

import java.util.*;

import static sketcher.scheduling.domain.QUser.user;


@Component
@RequiredArgsConstructor
public class AutoScheduling {

    private final UserService userService;
    private final EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository;
    private final PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    public static final int TIME_LENGTH = 24;
    public static final double FIXED_M3_RATIO = 0.3;
    public static final int MANAGER_DONE_REQUEST_AVG_PER_HOUR = 50;

    List<Manager> managerList = new ArrayList<>();
    List<Schedule> scheduleListByDAWN = new ArrayList<>();
    List<Schedule> scheduleListByMORNING = new ArrayList<>();
    List<Schedule> scheduleListByAFTERNOON = new ArrayList<>();
    List<Schedule> scheduleListByEVENING = new ArrayList<>();
    double totalCardValueAvg;
    int numOfCreatedScheduleNode = 0;

    public ArrayList<ResultScheduling> runAlgorithm(int[] userCode, int[] userCurrentTime, List<List<Integer>> hopeTimeList) {
        List<EstimatedNumOfCardsPerHour> cards = estimatedNumOfCardsPerHourRepository.findAll();
        List<PercentageOfManagerWeights> percentage = percentageOfManagerWeightsRepository.findAll();

        LinkedHashMap<Integer, Manager> managerNodes = makeManagerNode(userCode, userCurrentTime, hopeTimeList);

        int totalCardValueSum = 0;
        for (EstimatedNumOfCardsPerHour card : cards) {
            totalCardValueSum += card.getNumOfCards();
        }
        totalCardValueAvg = totalCardValueSum / cards.size();

        List<EstimatedNumOfCardsPerHour> cardsByDAWN = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByMORNING = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByAFTERNOON = new ArrayList<>();
        List<EstimatedNumOfCardsPerHour> cardsByEVENING = new ArrayList<>();


        for (EstimatedNumOfCardsPerHour card : cards) {
            if (0 <= card.getTime() && card.getTime() < 6) {
                cardsByDAWN.add(card);
            } else if (6 <= card.getTime() && card.getTime() < 12) {
                cardsByMORNING.add(card);
            } else if (12 <= card.getTime() && card.getTime() < 18) {
                cardsByAFTERNOON.add(card);
            } else if (18 <= card.getTime() && card.getTime() < 24) {
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
        makeManagerWeight(managerNodes, HopeTime.MORNING, percentage);
        //3. B타임 - 시간대별 필요인원 계산 -> 스케줄 노드 생성
        settingScheduleNodes(cardsByMORNING, scheduleListByMORNING);    // 스케줄 노드 값 설정(고정 매니저 포함 여부, 스케줄 가중치)
        //4. 이분매칭(dfs)
        bipartiteMatching(scheduleListByMORNING);   // 스케줄 노드에 배정된 매니저 코드 저장

        /* CYCLE FINISH */

        //5. C타임 스케줄링 배정 사이클
        makeManagerWeight(managerNodes, HopeTime.AFTERNOON, percentage);
        settingScheduleNodes(cardsByAFTERNOON, scheduleListByAFTERNOON);
        bipartiteMatching(scheduleListByAFTERNOON);

        //6. D타임 스케줄링 배정 사이클
        makeManagerWeight(managerNodes, HopeTime.EVENING, percentage);
        settingScheduleNodes(cardsByEVENING, scheduleListByEVENING);
        bipartiteMatching(scheduleListByEVENING);


        //7. A타임 스케줄링 배정 사이클
        makeManagerWeight(managerNodes, HopeTime.DAWN, percentage);
        settingScheduleNodes(cardsByDAWN, scheduleListByDAWN);
        bipartiteMatching(scheduleListByDAWN);




        /*RETURN*/
        ArrayList<ResultScheduling> schedulingsResults = new ArrayList<>(); // 타입 지정
        createResultSchedulingList(schedulingsResults, scheduleListByMORNING);
        createResultSchedulingList(schedulingsResults, scheduleListByAFTERNOON);
        createResultSchedulingList(schedulingsResults, scheduleListByEVENING);
        createResultSchedulingList(schedulingsResults, scheduleListByDAWN);

        return schedulingsResults;
    }

    private void createResultSchedulingList(ArrayList<ResultScheduling> schedulingsResults, List<Schedule> scheduleListByMORNING) {
        for (Schedule schedule : scheduleListByMORNING) {
            if (schedule.getManager() != null) {
                schedulingsResults.add(new ResultScheduling(schedule.getTime(), schedule.getManager().getCode(), schedule.getManager().getTotalAssignTime()));
            }
        }
    }

    private void bipartiteMatching(List<Schedule> scheduleList) {
        int count = 0;
        for (int i = 0; i < scheduleList.size(); i++) {
            if (firstDFS(scheduleList.get(i))) count++;   //매칭 개수
        }
    }

    public LinkedHashMap<Integer, Manager> makeManagerNode(int[] userCode, int[] userCurrentTime, List<List<Integer>> userHopeTimeList) {
        LinkedHashMap<Integer, Manager> managerNode = new LinkedHashMap<>();

        for (int i = 0; i < userCode.length; i++) {
            Manager manager = new Manager();
            manager.setCode(userCode[i]);
            manager.setTotalAssignTime(userCurrentTime[i]);

            List<Integer> startTimeList = userHopeTimeList.get(i);
            List<HopeTime> hopeTimeList = manager.getHopeTimeList();

            for (Integer startTime : startTimeList) {
                for (HopeTime hopeTime : HopeTime.values()) {
                    if (startTime == hopeTime.getStart_time()) {
                        hopeTimeList.add(hopeTime);
                        break;
                    }
                }
            }

            manager.setHopeTimeList(hopeTimeList);
            managerNode.put(userCode[i], manager);
            managerList.add(manager);
        }

        return managerNode;
    }

    public LinkedHashMap<Integer, Manager> makeManagerWeight(LinkedHashMap<Integer, Manager> managerNodes,
                                                             HopeTime hopeTime, List<PercentageOfManagerWeights> percentage) {
        managerList.clear();

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
            managerList.add(manager);
        }

        for (; i < middleManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(2);
            managerList.add(manager);

        }

        for (; i < lowManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(1);
            managerList.add(manager);

            for (Manager manager1 : managerList) {
                for (HopeTime time : manager1.getHopeTimeList()) {
                }
            }
        }

        return managerNodes;
    }

    private void settingScheduleNodes(List<EstimatedNumOfCardsPerHour> cards, List<Schedule> scheduleList) {

        int weight = 0;

        for (EstimatedNumOfCardsPerHour card : cards) {
            int numberOfManagers = (int) Math.ceil(card.getNumOfCards() / MANAGER_DONE_REQUEST_AVG_PER_HOUR);
            int numOfFixedManager = (int) Math.ceil(numberOfManagers * FIXED_M3_RATIO);
            if (card.getNumOfCards() < totalCardValueAvg / 2) {
                weight = 1;
            } else if (card.getNumOfCards() < totalCardValueAvg * 2) {
                weight = 2;
            } else {
                weight = 3;
            }

            int countOfFixedManager = 0;
            for (int i = 0; i < numberOfManagers; i++) {
                boolean managerWeightFlag = false;
                numOfCreatedScheduleNode++;
                if (countOfFixedManager < numOfFixedManager) {
                    managerWeightFlag = true;
                    countOfFixedManager++;
                }
                scheduleList.add(new Schedule(numOfCreatedScheduleNode, card.getTime(), weight, managerWeightFlag));
            }
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
                    return true;
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

