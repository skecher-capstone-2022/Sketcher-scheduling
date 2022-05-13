//package sketcher.scheduling.service;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import sketcher.scheduling.domain.ManagerWorkingSchedule;
//import sketcher.scheduling.domain.Schedule;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.ManagerWorkingScheduleDto;
//import sketcher.scheduling.dto.ScheduleDto;
//import sketcher.scheduling.dto.UserDto;
//import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
//import sketcher.scheduling.repository.ManagerWorkingScheduleRepository;
//import sketcher.scheduling.repository.ScheduleRepository;
//import sketcher.scheduling.repository.UserRepository;
//
//import java.util.Date;
//
//import static org.junit.Assert.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class ManagerWorkingScheduleServiceTest {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    UserService userService;
//    @Autowired
//    ScheduleService scheduleService;
//    @Autowired
//    ScheduleRepository scheduleRepository;
//    @Autowired
//    ManagerWorkingScheduleRepository managerWorkingScheduleRepository;
//    @Autowired
//    ManagerWorkingScheduleService managerWorkingScheduleService;
//
//    @Transactional
//    @Commit
//    @Test
//    public void 매니저_수행_스케줄_생성(){
//    //given
//        Date date = new Date();
//
//        UserDto user = UserDto.builder()
//                .id("min")
//                .authRole("user")
//                .password("1234")
//                .username("정민환")
//                .userTel("1234-5678")
//                .build();
//
//        String userId = userService.saveUser(user);
//
//        User userA = userRepository.findById(userId).get();
//
//    //when
//        ScheduleDto schedule = ScheduleDto.builder()
//                .scheduleDate(date)
//                .scheduleTime(3)
//                .workforce(100)
//                .expected_card_cnt(100)
//                .build();
//        Integer scheduleId = scheduleService.saveSchedule(schedule);
//        Schedule scheduleA = scheduleRepository.findById(scheduleId).get();
//
//    //then
//        ManagerWorkingScheduleDto managerWorkingScheduleDto = ManagerWorkingScheduleDto.builder()
//                .completed_card_cnt(100)
//                .user(userA)
//                .schedule(scheduleA)
//                .build();
//
//        Integer workingScheduleId = managerWorkingScheduleService.saveManagerWorkingSchedule(managerWorkingScheduleDto);
//        ManagerWorkingSchedule managerWorkingScheduleA = managerWorkingScheduleRepository.findById(workingScheduleId).get();
//        Assertions.assertEquals(managerWorkingScheduleA.getUser().getId() , "min");
//        Assertions.assertEquals(managerWorkingScheduleA.getSchedule().getWorkforce(), 100);
//
//    }
//}