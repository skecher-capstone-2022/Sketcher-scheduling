//package sketcher.scheduling.service;
//
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import sketcher.scheduling.domain.ManagerAssignSchedule;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.ManagerAssignScheduleDto;
//import sketcher.scheduling.dto.UserDto;
//import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
//import sketcher.scheduling.repository.ScheduleRepository;
//import sketcher.scheduling.repository.UserRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//public class ManagerAssignScheduleServiceTest {
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
//    ManagerAssignScheduleService managerAssignScheduleService;
//    @Autowired
//    ManagerAssignScheduleRepository managerAssignScheduleRepository;
//
////    @Test
////    @Transactional
////    @Commit
////    public void 매니저_배정_스케줄_생성 (){
////    //given
////        Date date = new Date();
////
////        UserDto user = UserDto.builder()
////                .id("min")
////                .authRole("user")
////                .password("1234")
////                .username("정민환")
////                .userTel("1234-5678")
////                .build();
////
////        userService.saveUser(user);
////
////        User userA = userRepository.findById(user.getId()).get();
////
////        ScheduleDto schedule = ScheduleDto.builder()
////                .scheduleDate(date)
////                .scheduleTime(3)
////                .workforce(100)
////                .expected_card_cnt(100)
////                .build();
////
////        Integer id = scheduleService.saveSchedule(schedule);
////        Schedule scheduleA = scheduleRepository.findById(id).get();
////
////        ManagerAssignScheduleDto managerAssignSchedule = ManagerAssignScheduleDto.builder()
////                .schedule_delete_req_check('N')
////                .user(userA)
////                .schedule(scheduleA)
////                .build();
////
////        managerAssignScheduleService.saveManagerAssignSchedule(managerAssignSchedule);
////
////        //when
////        List<ManagerAssignSchedule> userA_Schedule = managerAssignScheduleRepository.findByUser(userA);
////    //then
////        Assertions.assertEquals(userA_Schedule.get(0).getUser().getId(), userA.getId());
////        Assertions.assertEquals(userA_Schedule.get(0).getSchedule().getId(), scheduleA.getId());
////    }
//
//    @Test
//    @Transactional
//    @Commit
//    public void 일주일_단위_배정_스케줄() {
//        // given
//        UserDto userDto = UserDto.builder()
//                .id("userlhw")
//                .authRole("MANAGER")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .username("이혜원")
//                .userTel("010-1234-5678")
//                .build();
//        String userId = userService.saveUser(userDto);
//        User user = userRepository.findById(userId).get();
//
//        LocalDateTime date1 = LocalDateTime.of(2022,3,6,2,00);
//        LocalDateTime date2 = LocalDateTime.of(2022,3,6,4,00);
//        LocalDateTime date3 = LocalDateTime.of(2022,3,7,6,00);
//        LocalDateTime date4 = LocalDateTime.of(2022,3,7,9,00);
//        LocalDateTime date5 = LocalDateTime.of(2022,3,13,13,00);
//        LocalDateTime date6 = LocalDateTime.of(2022,3,13,16,00);
//        LocalDateTime date7 = LocalDateTime.of(2022,3,14,20,00);
//        LocalDateTime date8 = LocalDateTime.of(2022,3,14,22,00);
//
//        ManagerAssignScheduleDto assignSchedule = ManagerAssignScheduleDto.builder()
//                .user(user)
//                .scheduleDateTimeStart(date1)
//                .scheduleDateTimeEnd(date2)
//                .build();
//
//        ManagerAssignScheduleDto assignSchedule2 = ManagerAssignScheduleDto.builder()
//                .user(user)
//                .scheduleDateTimeStart(date3)
//                .scheduleDateTimeEnd(date4)
//                .build();
//
//        ManagerAssignScheduleDto assignSchedule3 = ManagerAssignScheduleDto.builder()
//                .user(user)
//                .scheduleDateTimeStart(date5)
//                .scheduleDateTimeEnd(date6)
//                .build();
//
//        ManagerAssignScheduleDto assignSchedule4 = ManagerAssignScheduleDto.builder()
//                .user(user)
//                .scheduleDateTimeStart(date7)
//                .scheduleDateTimeEnd(date8)
//                .build();
//
//        Integer assignedId1 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule);
//        Integer assignedId2 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule2);
//        Integer assignedId3 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule3);
//        Integer assignedId4 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule4);
//
//                //when
//        List<ManagerAssignSchedule> schedules = managerAssignScheduleService.findByUserId(userId);
//
//        //then
//        Assertions.assertEquals(schedules.size(), 2); // 4개의 스케줄 중 이번주 스케줄에 해당하는 것은 2개
//        Assertions.assertEquals(schedules.get(0).getId(), assignedId2);
//        Assertions.assertEquals(schedules.get(1).getId(), assignedId3);
//    }
//
//}