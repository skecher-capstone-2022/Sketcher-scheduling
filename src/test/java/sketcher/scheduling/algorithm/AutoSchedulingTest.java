package sketcher.scheduling.algorithm;

import com.querydsl.core.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.object.HopeTime;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static sketcher.scheduling.domain.QUser.user;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Rollback(value = false)
@Transactional
public class AutoSchedulingTest {

    @Autowired
    ManagerHopeTimeService managerHopeTimeService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    int scheduleNodeSize = 21;
    int managerNodeSize = 0;   //근무가능 매니저 수

    List<Schedule> scheduleList = new ArrayList<>();//Integer id, Integer time, Integer weight, boolean managerWeightFlag
    /*
    필요 스케줄 노드 수
    * S3 - 16
    * S2 - 4
    * S1 - 1
    * */
    int scheduleSectionSize[] = {16, 4, 1};

    List<Manager> managerList = new ArrayList<>();
    //Integer code, List<ManagerHopeTime> hopeTimeList, Integer hopeTimeCount, Integer totalAssignTime, Integer dayAssignTime, Integer weight
    /*
     * C타임 / ManagerHopeTime(0,)
     * C,D타임
     * B,C,D타임
     * A,B,C,D타임
     * */


    @Before
    @Transactional
    public void setUp() {

//        settingUsers();

//        List<User> allManager = userService.findAllManager();
//        managerNodeSize = allManager.size();


        settingScheduleNodes();
//        settingUsers();
        //case1. totalAssignTime을 고려x
/*        for (int i = 0; i < managerNodeSize; i++) {
            User user = allManager.get(i);
            int weight = 0;
            int monthValue = user.getUser_joinDate().getMonthValue();
            if (monthValue == 6) {
                weight = 1;
            } else if (monthValue == 5) {
                weight = 2;
            } else {
                weight = 3;
            }

            List<ManagerHopeTime> managerHopeTimeList = managerHopeTimeService.findManagerHopeTimeByUser(user);
            managerList.add(new Manager(user.getCode(), managerHopeTimeList, managerHopeTimeList.size(),
                    0, 0, weight));
        }


        for (Manager manager : managerList) {
            System.out.println(manager.getCode() + " : M" + manager.getWeight() + ", 희망시간 개수 " + manager.getHopeTimeCount());
        }*/
    }

    @Test
    public void makeManagerWeightTest() {
        int[] userCode = makeUserCodeArray();
        int[] userCurrent = new int[managerNodeSize];

        List<PercentageOfManagerWeights> percentage = percentageOfManagerWeightsRepository.findAll();
        LinkedHashMap<Integer, Manager> managerNode = makeManagerNode(userCode, userCurrent);

        makeManagerWeight(managerNode, HopeTime.DAWN, percentage);
    }

    public LinkedHashMap<Integer, Manager> makeManagerNode(int[] userCode, int[] userCurrentTime) {
        LinkedHashMap<Integer, Manager> managerNode = new LinkedHashMap<>();

        for (int i = 0; i < userCode.length; i++) {
            Manager manager = new Manager();
            manager.setCode(userCode[i]);
            manager.setTotalAssignTime(0);

            managerNode.put(userCode[i], manager);
        }

        return managerNode;
    }

    public LinkedHashMap<Integer, Manager> makeManagerWeight(LinkedHashMap<Integer, Manager> managerNodes,
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
            manager.setWeight(3);
        }

        for (; i < middleManager; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(2);
        }

        for (; i < count; i++) {
            Tuple tuple = joinDateByHopeTime.get(i);
            Integer code = tuple.get(user.code);
            Manager manager = managerNodes.get(code);
            manager.setWeight(1);
        }

        return managerNodes;
    }


    private void settingScheduleNodes() {
        Integer tempTime = 13;

        Integer weightType[] = {3, 2, 1};
        boolean managerWeightFlag = false;

        int count = 0;      //변수값 세팅시에 scheduleSectionSize만큼 배정했는지 확인하기 위한 카운팅 변수
        int pointer = 0;    //scheduleSectionSize, weightType를 가리키는 포인터


        for (int i = 1; i <= scheduleNodeSize; i++) {
            if (weightType[pointer] == 3) {
                managerWeightFlag = true;
            }
            scheduleList.add(new Schedule(i, tempTime, weightType[pointer], managerWeightFlag));
            count++;
            if (scheduleSectionSize[pointer] == count) {
                tempTime++;
                pointer++;
                count = 0;
            }
            managerWeightFlag = false;
        }
    }


    private void settingUsers() {
        LocalDateTime date1 = LocalDateTime.of(2022, 3, 10, 1, 00);
        LocalDateTime date3 = LocalDateTime.of(2022, 4, 10, 20, 00);
        LocalDateTime date5 = LocalDateTime.of(2022, 5, 10, 17, 00);
        LocalDateTime date7 = LocalDateTime.of(2022, 6, 10, 7, 00);
        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        localDateTimeList.add(date1);
        localDateTimeList.add(date3);
        localDateTimeList.add(date5);
        localDateTimeList.add(date7);

        String names[] = {"박태영", "정민환", "이혜원", "김희수", "김민준", "박서준", "임도윤", "정예준", "박시우", "정하준", "서주원", "유지호", "성지훈", "김준우", "박건우", "박서연", "이서윤", "이지우", "김서현", "박하은", "김하은", "김민서", "유민서", "박지민", "김희철", "김채원", "이도현", "김연우", "유다은", "김지원", "서지원", "이수빈", "김예린",
                "이준영", "박시후", "김진우", "정승우", "박채은", "채유나", "김가은", "박서영", "윤민지", "최예나", "최수민", "강수현", "이동현", "최한결", "김재원", "서민우", "김연서", "강다연", "정나윤", "김성현", "김우빈", "정지한", "최예성", "한나은", "홍예지"};


        for (int i = 0; i < names.length; i++) {
            UserDto userA = UserDto.builder()
                    .id("user" + (i + 1))
                    .authRole("MANAGER")
                    .password(new BCryptPasswordEncoder().encode("12345"))
                    .username(names[i])
                    .userTel("010-1234-5678")
                    .user_joinDate(localDateTimeList.get((i + 1) % 4))
                    .managerScore(5.0)
                    .build();
            String user1 = userService.saveUser(userA);
//            User userT = userA.toEntity();
            User userT = userRepository.findById("user" + (i + 1)).get();

            if (i < 5) {
                setHopeTime(userT, 0, 6);
            } else if (i < 10) {
                setHopeTime(userT, 6, 12);
            } else if (i < 15) {
                setHopeTime(userT, 0, 6);
                setHopeTime(userT, 6, 12);
            } else if (i < 20) {
                setHopeTime(userT, 12, 18);
                setHopeTime(userT, 18, 24);
            } else if (i < 30) {
                setHopeTime(userT, 6, 12);
                setHopeTime(userT, 12, 18);
                setHopeTime(userT, 18, 24);
            } else if (i >= 30) {
                setHopeTime(userT, 0, 6);
                setHopeTime(userT, 6, 12);
                setHopeTime(userT, 12, 18);
                setHopeTime(userT, 18, 24);
            }
        }

    }


    int[] makeUserCodeArray() {
        List<User> allManager = userService.findAllManager();
        int[] userCode = new int[allManager.size()];

        for (int i = 0; i < allManager.size(); i++) {
            User user = allManager.get(i);
            userCode[i] = user.getCode();
        }

        return userCode;
    }

    void setHopeTime(User userT, int i2, int i3) {
        ManagerHopeTimeDto hopeC = ManagerHopeTimeDto.builder()
                .start_time(i2)
                .finish_time(i3)
                .user(userT)
                .build();
        managerHopeTimeService.saveManagerHopeTime(hopeC);
    }

    @Test
    @Transactional
    public void dfsTest() {
        int count = 0;
        for (int i = 0; i < scheduleNodeSize; i++) {
            if (dfs(scheduleList.get(i))) count++;   //매칭 개수
        }
        System.out.println("matching count :" + count);
        for (Schedule schedule : scheduleList) {
            System.out.println(schedule.getId() + " : " + schedule.getTime() + "시, S" + schedule.getWeight() + ", M3매니저 필수 여부 " + schedule.isManagerWeightFlag() + ", 배정매니저번호 " + schedule.getManager().getCode());
        }
    }

    public boolean dfs(Schedule scheduleNode) {
//        int pointer = settingScheduleNodeTotalSize(scheduleNode);
//        for (int i = 0; i < scheduleSectionSize[pointer]; i++) {
        for (Manager manager : managerList) {
            Schedule alreadyExistingScheduleNode = manager.findScheduleByTime(scheduleNode.getTime());
            if (alreadyExistingScheduleNode != null)
                continue;
            //alreadyExistingScheduleNode == null인 경우
            if (scheduleNode.isManagerWeightFlag()) {    //무조건 매니저는 M3여야 함
                if (manager.getWeight() != 3) {
                    continue;
                }
            }
            if (alreadyExistingScheduleNode == null || dfs(alreadyExistingScheduleNode)) {
                manager.updateAssignScheduleList(alreadyExistingScheduleNode, scheduleNode);
                scheduleNode.setManager(manager);
                return true;
            }
        }
        return false;
    }


    private int settingScheduleNodeTotalSize(Schedule scheduleNode) {
        int pointer = -1;
        if (scheduleNode.getWeight() == 3) {
            pointer = 0;
        } else if (scheduleNode.getWeight() == 2) {
            pointer = 1;
        } else if (scheduleNode.getWeight() == 1) {
            pointer = 2;
        }
        return pointer;
    }
}

//    /*
//#define MAX 1001
//
//vector<int> a[MAX];
//int d[MAX];
//bool c[MAX];
//int n, m;
//
//bool dfs(int x) {
//	for(int i = 0; i < a[x].size(); i++) {
//		int t = a[x][i];
//		if(c[t]) continue;
//		c[t] = true;
//		if(d[t] == 0 || dfs(d[t])) {
//			d[t] = x;
//			return true;
//		}
//	}
//	return false;
//}