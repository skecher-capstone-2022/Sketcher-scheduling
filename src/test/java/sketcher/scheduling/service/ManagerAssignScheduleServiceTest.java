package sketcher.scheduling.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduelDto;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ManagerHopeTimeRepository;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManagerAssignScheduleServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ManagerAssignScheduleService managerAssignScheduleService;
    @Autowired
    ManagerAssignScheduleRepository managerAssignScheduleRepository;

    @Test
    @Transactional
    @Commit
    public void 매니저_배정_스케줄_생성 (){
    //given
        Date date = new Date();

        UserDto user = UserDto.builder()
                .id("min")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();

        userService.saveUser(user);

        User userA = userRepository.findById(user.getId()).get();

        ScheduleDto schedule = ScheduleDto.builder()
                .scheduleDate(date)
                .scheduleTime(3)
                .workforce(100)
                .expected_card_cnt(100)
                .build();

        Integer id = scheduleService.saveSchedule(schedule);
        Schedule scheduleA = scheduleRepository.findById(id).get();

        ManagerAssignScheduelDto managerAssignSchedule = ManagerAssignScheduelDto.builder()
                .schedule_delete_req_check('N')
                .user(userA)
                .schedule(scheduleA)
                .build();

        managerAssignScheduleService.saveManagerAssignSchedule(managerAssignSchedule);

        //when
        List<ManagerAssignSchedule> userA_Schedule = managerAssignScheduleRepository.findByUser(userA);
    //then
        Assertions.assertEquals(userA_Schedule.get(0).getUser().getId(), userA.getId());
        Assertions.assertEquals(userA_Schedule.get(0).getSchedule().getId(), scheduleA.getId());
    }
}