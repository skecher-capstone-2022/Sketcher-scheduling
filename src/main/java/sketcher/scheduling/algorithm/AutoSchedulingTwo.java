package sketcher.scheduling.algorithm;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.dto.EstimatedNumOfCardsPerHourDto;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@NoArgsConstructor
public class AutoSchedulingTwo {
    private final ManagerHopeTimeService managerHopeTimeService;
    private final UserService userService;
    private final EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository;


    //    private static int[] time = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
//    private static int[] value = {65, 0, 0, 93, 195, 222, 289, 181, 124, 271, 178, 89}; // 각각 0, 2 ,4, 6, 8, 10, 12, 14, 16, 18, 20, 22
    private static int[] value; // 각각 0, 2 ,4, 6, 8, 10, 12, 14, 16, 18, 20, 22
    private static ArrayList<Integer>[] hopeTime;
    private static ArrayList<Integer>[] schedule;
    private static ArrayList<Integer>[] checkSchedule;



    // db 에서 가져와서 사용.

    private static int[] scheduleWeight;

    private static int[] managerWeight;
    private static int[] countAssignTime;
    private static int[] descTime;
    private static int descIndex = 1;  // 배정시간 내림차순 정렬 인덱스


    /**
     * 문제점
     */
    private static int weightCount1[] = {0, -1, -1, -1, -1};
    private static int weightCount2[] = {0, -1, -1, -1, -1};
    private static int weightCount3[] = {0, -1, -1, -1, -1};


    public ArrayList<ResultScheduling> runAlgorithm(int userCode[], int userCurrentTime[]) {
        List<EstimatedNumOfCardsPerHour> Cards = estimatedNumOfCardsPerHourRepository.findAll();
        int managerSize = userCode.length;      // alreadyMatch 로직 돌릴 때 0 번 매니저랑 스케줄 값 0 이 똑같아서 1번부터 시작해야함.
        hopeTime = new ArrayList[managerSize]; // 매니저의 희망 시간을 담는 배열
        schedule = new ArrayList[(time.length + 1) / 2]; // 스케줄표 배열. UserCode 를 담기 위함 -> 12시간만 담기 위해서.
        checkSchedule = new ArrayList[(time.length + 1) / 2]; // 해당 스케줄 노드에 스케줄이 담겼는지 체크하기 위한 배열
        countAssignTime = new int[managerSize]; // 스케줄 노드 중 어떤 배열이 담겼는지 확인 위함
        scheduleWeight = new int[Cards.size()]; // 스케줄 가중치 계산 -> value 를 기준으로 가중치를 1 ~ 3 으로 적용.
        managerWeight = new int[managerSize + 1]; // 매니저 가중치
        descTime = new int[managerSize];  // 시간 내림차순 정렬 인덱스 계산
        value = new int[Cards.size()];

        for (int i = 1; i < managerSize; i++) {
            hopeTime[i] = new ArrayList<>();        // 배열 안에 List 생성 , 매니저수만큼
        }

        for (int i = 0; i < 12; i++) { // 기존 24 시간에서 2시간씩 배정할 것이므로 /2 -> 12시간으로 바꿈.
            schedule[i] = new ArrayList<>();    // 배열 안에 List 생성
            checkSchedule[i] = new ArrayList<>();   // 배열 안에 List 생성
        }

        for (int i = 1; i < userCode.length; i++) {
            List<ManagerHopeTime> hopeTimeByUserCode = managerHopeTimeService.getHopeTimeByUserCode(userCode[i]);
            for (int j = 0; j < hopeTimeByUserCode.size(); j++) {
                hopeTime[i].add(j, hopeTimeByUserCode.get(j).getStart_time());
            }
        }
        for (int i = 0; i < 12; i++) { // 시간
            for (int j = 0; j < 10; j++) { // 노드 수
                checkSchedule[i].add(j, 0);
                schedule[i].add(j, 0);
            }
        }
        for (int i = 0; i < Cards.size(); i++) {
            value[i] =  Cards.get(i).getNumOfCards();
        }




        /**
         * 스케줄 가중치 계산을 위한 로직.
         * 매니저 가중치 계산을 위한 로직
         */
        scheduleWeightLogic();
        managerWeightLogic(managerSize);

        int count = 0;

        /**
         * 배정시간 오름차순 로직.
         */
        descIndex = 1;

        sortDesc(userCurrentTime, managerSize);

        for (int k = 0; k < 2; k++) {
            for (int i = 1; i < managerSize; i++) {
                if (check4Hours(countAssignTime, descTime[i])) {
                    if (dfs(descTime[i]))
                        count++;
                }
            }
        }

        for (int i = 1; i < countAssignTime.length; i++) {
            userCurrentTime[i] += countAssignTime[i];
        }


        ArrayList<ResultScheduling> schedulingsResults = new ArrayList<>(); // 타입 지정
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                int startTime = i;
                int userindex = schedule[i].get(j);
                if (userindex != 0) {
                    schedulingsResults.add(new ResultScheduling(startTime * 2, userCode[userindex], userCurrentTime[userindex]));
                }
            }
        }


        return schedulingsResults;
    }


    /**
     * 내림차순 문제
     */
    private void sortDesc(int[] userCurrentTime, int managerSize) {
        for (int i = 1; i < managerSize; i++) {
            if (userCurrentTime[i] >= 0 && userCurrentTime[i] < 3) {
                descTime[descIndex] = i;
                descIndex++;
            }
        }
        for (int i = 1; i < managerSize; i++) {
            if (userCurrentTime[i] >= 3 && userCurrentTime[i] < 6) {
                descTime[descIndex] = i;
                descIndex++;
            }
        }
        for (int i = 1; i < managerSize; i++) {
            if (userCurrentTime[i] >= 6 && userCurrentTime[i] < 10) {
                descTime[descIndex] = i;
                descIndex++;
            }
        }
        for (int i = 1; i < managerSize; i++) {
            if (userCurrentTime[i] >= 10) {
                descTime[descIndex] = i;
                descIndex++;
            }
        }
    }

    private boolean dfs(int index) {
        int i;

//        for (i = 1; i <= 5; i++) {

        for (int m = 0; m < hopeTime[index].size(); m++) {

            int managerHopeTime = hopeTime[index].get(m);

            /**
             * 근무시간이 4시간 초과하면 break 하고 그냥 끝냄.
             * 현재 오류 발생.. 수정 필요
             */
//            if (countAssignTime[index] > 4)
//                break;
            /**
             * 희망시간이 0~6시, 6~12, 12~18, 18~24 중 어디에 해당하는지 check
             * 각 희망타임 별 6시간 중 들어갈 수 있는 곳 check
             * 각 희망타임에서 총 10개의 노드를 각각 체크
             * 1 이라면 이미 배정돼 있는 상태 continue
             * 그렇지 않다면 (0 이라면) 배정돼 있지 않은 상태 -> dfs 알고리즘.
             */
            if (scheduleWeight[m] == 1)  // 스케줄 가중치가 1인 경우
            {
                if (scheduleWeight1(index))
                    continue;
            } else if (scheduleWeight[m] == 2) {
                if (scheduleWeight2(index))
                    continue;
            } else if (scheduleWeight[m] == 3) {
                if (scheduleWeight3(index))
                    continue;
            }

            if (managerHopeTime == 0) {
                if (schedulingLogicDawn(index)) return true;
            }

            if (managerHopeTime == 6) {
                if (schedulingLogicMorning(index)) return true;
            }

            if (managerHopeTime == 12) {
                if (schedulingLogicAfternoon(index)) return true;
            }

            if (managerHopeTime == 18) {
                if (schedulingLogicEvening(index)) return true;
            }
        }
//        }
        return false;
    }

    private boolean check4Hours(int[] countAssignTime, int index) {
        boolean present = Arrays.stream(countAssignTime).findAny().isPresent();     // 값이 없는 매니저의 경우 true 로 반환돼서 명시적으로 false 로 설정
        if (!present)
            return false;

        if (countAssignTime[index] == 2)
            return false;


        return true;
    }

    private boolean schedulingLogicEvening(int index) {
        int k = 0;
        /**
         * 해당 시간대에 이미 매칭됐다면 false 반환하고 스케줄링 로직 종료.
         */
        for (int i = 9; i < 12; i++) {

            boolean alreadyMatch = schedule[i].stream().anyMatch(s -> s.equals(index));
            if (alreadyMatch) {
                return false;
            }
        }

        for (int j = 9; j < 12; j++) {
            for (k = 0; k < scheduleNodeLogic(j, k); k++) { // 기존 3개 시간대 10 개 노드

                if (checkSchedule[j].get(k).equals(1))
                    continue;

                else
                    checkSchedule[j].add(k, 1);

                if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
                    schedule[j].add(k, index);
                    countAssignTime[index]++;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean schedulingLogicAfternoon(int index) {
        int k = 0;
        for (int i = 6; i < 9; i++) {
            boolean alreadyMatch = schedule[i].stream().anyMatch(s -> s.equals(index));
            if (alreadyMatch) {
                return false;
            }
        }
        for (int j = 6; j < 9; j++) {
            for (k = 0; k < scheduleNodeLogic(j, k); k++) { // 기존 3개 시간대 10 개 노드

                if (checkSchedule[j].get(k).equals(1))
                    continue;
                else
                    checkSchedule[j].add(k, 1);

                if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
                    schedule[j].add(k, index);
                    countAssignTime[index]++;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean schedulingLogicMorning(int index) {
        int k = 0;
        for (int i = 3; i < 6; i++) {
            boolean alreadyMatch = schedule[i].stream().anyMatch(s -> s.equals(index));
            if (alreadyMatch) {
                return false;
            }
        }

        for (int j = 3; j < 6; j++) {
            for (k = 0; k < scheduleNodeLogic(j, k); k++) { // 기존 3개 시간대 10 개 노드

                if (checkSchedule[j].get(k).equals(1))
                    continue;
                /**
                 * 배정되있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
                 * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
                 * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
                 */

                else
                    checkSchedule[j].add(k, 1);

                if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
                    schedule[j].add(k, index);
                    countAssignTime[index]++;
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 배정되어 있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
     * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
     * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
     */
    private boolean schedulingLogicDawn(int index) {
        int k = 0;
        for (int i = 0; i < 3; i++) {
            boolean alreadyMatch = schedule[i].stream().anyMatch(s -> s.equals(index));
            if (alreadyMatch) {
                return false;
            }
        }

        for (int j = 0; j < 3; j++) { // 기존 3개 시간대 노드
            for (k = 0; k < scheduleNodeLogic(j, k); k++) {
                if (checkSchedule[j].get(k).equals(1))
                    continue;
                else
                    checkSchedule[j].add(k, 1);

                if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
                    schedule[j].add(k, index);
                    countAssignTime[index]++;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 스케줄 가중치 3 -> 매니저 가중치 3 = 50% , 매니저 가중치 2 = 30% 매니저 가중치 1 = 20% -> 10 명
     * 오전 6시 -> 스케줄 가중치 3 매니저 가중치 3 = 5명, 2 3명, 1 1명.
     * 오전 7시 ->
     * 오후 1시 ->
     * 배정이 완료되는 순서 랜덤 -> 저 가중치 값이 막 어긋나는중..
     *
     */
    private boolean scheduleWeight1(int index) {
        if (managerWeight[index] == 1) {        // 매니저 가중치가 1인 사람
            weightCount1[1]++;
            if (weightCount1[1] == 3) {
                weightCount1[1] = -1;
                return true;
            }
        } else if (managerWeight[index] == 2) {
            weightCount1[2]++;
            if (weightCount1[2] == 1) {
                weightCount1[2] = -1;
                return true;
            }
        } else if (managerWeight[index] == 3) {
            weightCount1[3]++;
            if (weightCount1[3] == 1) {
                weightCount1[3] = -1;
                return true;
            }
        }
        return false;
    }

    private boolean scheduleWeight2(int index) {
        if (managerWeight[index] == 1) {        // 매니저 가중치가 1인 사람
            weightCount2[1]++;
            if (weightCount2[1] == 2) {
                weightCount2[1] = -1;
                return true;
            }
        } else if (managerWeight[index] == 2) {
            weightCount2[2]++;
            if (weightCount2[2] == 4) {
                weightCount2[2] = -1;
                return true;
            }
        } else if (managerWeight[index] == 3) {
            weightCount2[3]++;
            if (weightCount2[3] == 4) {
                weightCount2[3] = -1;
                return true;
            }
        }
        return false;
    }

    private boolean scheduleWeight3(int index) {
        if (managerWeight[index] == 1) {        // 매니저 가중치가 1인 사람
            weightCount3[1]++;
            if (weightCount3[1] == 2) {
                weightCount3[1] = -1;
                return true;
            }
        } else if (managerWeight[index] == 2) {
            weightCount3[2]++;
            if (weightCount3[2] == 4) {
                weightCount3[2] = -1;
                return true;
            }
        } else if (managerWeight[index] == 3) {
            weightCount3[3]++;
            if (weightCount3[3] == 4) {
                weightCount3[3] = -1;
                return true;
            }
        }
        return false;
    }


    private int scheduleNodeLogic ( int j, int k){

        if (value[j] == 0)
            k = 0;
        else if (value[j] < 30)
            k = 1;
        else if (value[j] >= 30 && value[j] < 60)
            k = 2;
        else if (value[j] >= 60 && value[j] < 90)
            k = 3;
        else if (value[j] >= 90 && value[j] < 120)
            k = 4;
        else if (value[j] >= 120 && value[j] < 150)
            k = 5;
        else if (value[j] >= 150 && value[j] < 180)
            k = 6;
        else if (value[j] >= 180 && value[j] < 210)
            k = 7;
        else if (value[j] >= 210 && value[j] < 240)
            k = 8;
        else if (value[j] >= 240 && value[j] < 270)
            k = 9;
        else if (value[j] >= 270 && value[j] <= 300)
            k = 10;
        return k;
    }

    private void scheduleWeightLogic () {
        for (int i = 0; i < value.length; i++) {
            int weight = 0;
            if (value[i] <= 150) {
                weight = 1;
                scheduleWeight[i] = weight;
            } else if (value[i] > 150 && value[i] <= 210) {
                weight = 2;
                scheduleWeight[i] = weight;
            } else if (value[i] > 210 && value[i] <= 300) {
                weight = 3;
                scheduleWeight[i] = weight;
            }
        }
    }

    private void managerWeightLogic ( int managerSize){
        for (int i = 1; i < managerSize; i++) {
            if(i < 40)
                if (i % 2 == 0)
                    managerWeight[i] = 3;
                else if(i % 2 == 1)
                    managerWeight[i] = 2;
                else if(i>= 40 && i < 50)
                    managerWeight[i] = 1;
                else
                    managerWeight[i] = 2;
        }
    }

}