package sketcher.scheduling.algorithm;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

@Component
public class AutoSchedulingTwo {


    private final ManagerHopeTimeService managerHopeTimeService;
    private final UserService userService;

    // 생성자 직접 주입
    public AutoSchedulingTwo(ManagerHopeTimeService managerHopeTimeService, UserService userService) {
        this.managerHopeTimeService = managerHopeTimeService;
        this.userService = userService;
    }

    private static ArrayList<Integer>[] hopeTime;
    private static ArrayList<Integer>[] schedule;
    private static ArrayList<Integer>[] checkSchedule;

    private static int[] time = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private static int[] countAssignTime;
    private static int[] managerAssigned;


    public void runAlgorithm() throws Exception {
        List<User> allManager = userService.findAll();
        int managerSize = allManager.size();    // 전체 매니저 수 만큼 배열을 생성하기 위함.

        hopeTime = new ArrayList[managerSize + 1]; // 매니저의 희망 시간을 담는 배열
        schedule = new ArrayList[time.length + 1]; // 스케줄표 배열. UserCode 를 담기 위함
        checkSchedule = new ArrayList[time.length + 1]; // 해당 스케줄 노드에 스케줄이 담겼는지 체크하기 위한 배열
        countAssignTime = new int[managerSize]; // 스케줄 노드 중 어떤 배열이 담겼는지 확인 위함
        managerAssigned = new int[managerSize]; // 몇번이 배정됐는지 확인 위함
        // countAssignTime 과 managerAssigned 는 아마 삭제 예정일듯?.. 겹치는 기능인듯함. 무시 요망.

        for (int i = 1; i < 4; i++) {
            hopeTime[i] = new ArrayList<>();        // 배열 안에 List 생성
        }

        for (int i = 0; i < 10; i++) {
            schedule[i] = new ArrayList<>();    // 배열 안에 List 생성
            checkSchedule[i] = new ArrayList<>();   // 배열 안에 List 생성
        }
        /**
         * 매니저는 1번부터 시작 -> userCode 가 그렇기 때문.
         * 1번 매니저의 hopeTime 개수를 체크하고, 체크한 개수만큼 schedule 배열에 가능한 시간대를 저장. 0, 6, 12, 18 이 저장됨.
         * hopeTime[1].add(0, 0) , hopeTime[1].add(0, 6), hopeTime[1].add(0, 12) => userCode 1번인 사람 희망시작시간 0, 6, 12 저장
         */
        List<ManagerHopeTime> findAllHopeTime = managerHopeTimeService.findAll();
        for (int i = 1; i < 4; i++) {
            List<ManagerHopeTime> hopeTimeByUserCode = managerHopeTimeService.getHopeTimeByUserCode(i);
            for (int j = 0; j < hopeTimeByUserCode.size(); j++) {
                hopeTime[i].add(j, hopeTimeByUserCode.get(j).getStart_time());
            }
        }

        /**
         * 이 부분 문제.. 이중 중첩 배열인데 생각한대로 안되는중..
         * checkSchedule을 53행에서 총길이 25만큼 생성
         * 그 후 64행에서 내부 배열을 10개만큼 생성
         * 그럼
         *         for (int i = 0; i < 25; i++) {
         *             for (int j = 0; j < 10; j++) {
         *                 checkSchedule[i].add(j, 0);
         *                 schedule[i].add(j, 0);
         *             }
         *         }
         *  가 돼야하는거같은데 i 를 25 로 바꾸면 null Exception 터짐..
         *  이것 때문에 현재 0 ~ 6 시 시간대만 설정이 가능.
         */
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                checkSchedule[i].add(j, 0);
                schedule[i].add(j, 0);
            }
        }

        /**
         * dfs 알고리즘 수행.
         * managerHopeTime 테이블에 중간중간 매니저가 비어있어서 null 들어가는 오류 때문에 임시로 3번 매니저까지만 확인할 수 있도록 함.
         */
        int count = 0;
        for (int i = 1; i < 4; i++) {
            if (dfs(i))
                count++;
        }

        /**
         * 로그 찍기 위한 테스트 로그용.
         */
        System.out.println(" =================================== ");
        System.out.println("count = " + count);
        for (int i = 0; i < managerSize; i++)
            System.out.println("countAssignTime = " + countAssignTime[i]);
    }

    private boolean dfs(int index) {
        int i;
        /**
         * index -> 현재 매니저 code
         * managerHopeTime 테이블에 중간중간 매니저가 비어있어서 null 들어가는 오류 때문에 임시로 3번 매니저까지만 확인할 수 있도록 함.
         * 매니저가 희망한 시간의 start_time 을 managerHopeTime 에 대입.
         * 알고리즘 수행
         */
        for (i = 1; i <= index; i++) {
            for (int m = 0; m < hopeTime[index].size(); m++) {
                int managerHopeTime = hopeTime[index].get(m);

                /**
                 * 근무시간이 4시간 초과하면 break 하고 그냥 끝냄.
                 * 현재 오류 발생.. 수정 필요
                 */
//                if (countAssignTime[index + 1] > 4)
//                    break;
                /**
                 * 희망시간이 0~6시, 6~12, 12~18, 18~24 중 어디에 해당하는지 check
                 * 각 희망타임 별 6시간 중 들어갈 수 있는 곳 check
                 * 각 희망타임에서 총 10개의 노드를 각각 체크
                 * 1 이라면 이미 배정돼 있는 상태 continue
                 * 그렇지 않다면 (0 이라면) 배정돼 있지 않은 상태 -> dfs 알고리즘.
                 */
//                if (managerHopeTime >= 0 && managerHopeTime < 6) {
                if (managerHopeTime == 0) {
                    for (int j = 0; j < 6; j++) {
                        for (int k = 0; k < 10; k++) {
                            if (checkSchedule[j].get(k).equals(1))
                                continue;
                            /**
                             * 배정되어 있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
                             * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
                             * dfs 재귀 부분 정확히 모르겠음..
                             * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
                             */
                            else
                                checkSchedule[j].add(k, 1);

                            if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
                                schedule[j].add(k, index);
                                countAssignTime[index]++;
                                managerAssigned[index] = index;
                                System.out.println("managerAssigned = " + managerAssigned[index]);
                                return true;
                            }
                        }
                    }
                }


                /**
                 * 6 ~ 12 알고리즘
                 */
//                if (managerHopeTime == 6) {
//                    for (int j = 6; j < 12; j++) {
//                        for (int k = 0; k < 10; k++) {
//                            if (checkSchedule[j].get(k).equals(1))
//                                continue;
//                            /**
//                             * 배정되있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
//                             * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
//                             * dfs 재귀 부분 모르겠음.. 어떤 조건에 돌아가는건가?..
//                             * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
//                             */
//                            else
//                                checkSchedule[j].add(k, 1);
//
//                            if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
//                                schedule[j].add(k, index);
//                                countAssignTime[index]++;
//                                managerAssigned[index] = index;
//                                System.out.println("managerAssigned = " + managerAssigned[index]);
//                                return true;
//                            }
//                        }
//                    }
//                }
//
//
//                /**
//                 * 12 ~ 18
//                 */
//                if (managerHopeTime == 12) {
//                    for (int j = 12; j < 18; j++) {
//                        for (int k = 0; k < 10; k++) {
//                            if (checkSchedule[j].get(k).equals(1))
//                                continue;
//                            /**
//                             * 배정되있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
//                             * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
//                             * dfs 재귀 부분 모르겠음.. 어떤 조건에 돌아가는건가?..
//                             * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
//                             */
//                            else
//                                checkSchedule[j].add(k, 1);
//
//                            if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
//                                schedule[j].add(k, index);
//                                countAssignTime[index]++;
//                                managerAssigned[index] = index;
//                                System.out.println("managerAssigned = " + managerAssigned[index]);
//                                return true;
//                            }
//                        }
//                    }
//                }
//
//                /**
//                 * 18
//                 */
//                if (managerHopeTime == 18) {
//                    for (int j = 18; j < 24; j++) {
//                        for (int k = 0; k < 10; k++) {
//                            if (checkSchedule[j].get(k).equals(1))
//                                continue;
//                            /**
//                             * 배정되있지 않은 경우, 상태를 1 로 변경 (배정된 상태)
//                             * 스케줄의 0번째 시간의 0번째 노드에 UserCode 를 대입.
//                             * dfs 재귀 부분 모르겠음.. 어떤 조건에 돌아가는건가?..
//                             * countAssignTime[i]++ -> 스케줄이 배정될 때마다 근무시간 +1
//                             */
//                            else
//                                checkSchedule[j].add(k, 1);
//
//                            if (schedule[j].get(k) == 0 || dfs(schedule[j].get(k))) {
//                                schedule[j].add(k, index);
//                                countAssignTime[index]++;
//                                managerAssigned[index] = index;
//                                System.out.println("managerAssigned = " + managerAssigned[index]);
//                                return true;
//                            }
//                        }
//                    }
//                }

            }
        }

        return false;
    }
}
