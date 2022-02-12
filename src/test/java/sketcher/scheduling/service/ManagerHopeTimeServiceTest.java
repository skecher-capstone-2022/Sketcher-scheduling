package sketcher.scheduling.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ManagerHopeTimeRepository;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManagerHopeTimeServiceTest {

    @Autowired
    ManagerHopeTimeRepository managerHopeTimeRepository;
    @Autowired
    ManagerHopeTimeService managerHopeTimeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @Test
    @Transactional
    @Commit
    public void 매니저_희망_시간_생성() {
        //given
        /**
         * userA 가져옴
         */
        UserDto user = UserDto.builder()
                .id("min")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();

        String user1 = userService.saveUser(user);
        User userA = userRepository.findById(user1).get();

        /**
         * ManagerHopeTime 을 userA 에 저장
         *  / 연관관계 편의 메소드로 인해 User 클래스의 HopeTime 에 입력
         */
        ManagerHopeTimeDto managerHopeTime1 = ManagerHopeTimeDto.builder()
                .start_time(3)
                .finish_time(5)
                .user(userA)
                .build();

        ManagerHopeTimeDto managerHopeTime2 = ManagerHopeTimeDto.builder()
                .start_time(1)
                .finish_time(10)
                .user(userA)
                .build();


        managerHopeTimeService.saveManagerHopeTime(managerHopeTime1);
        managerHopeTimeService.saveManagerHopeTime(managerHopeTime2);

        List<ManagerHopeTime> managerHopeTimeByUsers = managerHopeTimeRepository.findManagerHopeTimeByUser(userA);
        ManagerHopeTime managerHopeTimeA = managerHopeTimeByUsers.get(0);
        ManagerHopeTime managerHopeTimeB = managerHopeTimeByUsers.get(1);

        //    //when

        Integer start_timeA = managerHopeTimeA.getStart_time(); // -> 3
        Integer start_timeB = managerHopeTimeB.getStart_time(); // -> 1
//        //then
        /**
         * user에서 가져온 userA의 hopetime 과  managerHopeTimeRepo 에서 가져온 HopeTime 일치 확인
         */
        Integer user_A_Start_TimeA = userA.getManagerHopeTimeList().get(0).getStart_time(); // -> 3
        Integer user_A_Start_TimeB = userA.getManagerHopeTimeList().get(1).getStart_time(); // -> 1

        Assertions.assertEquals(user_A_Start_TimeA, start_timeA);
    }
}