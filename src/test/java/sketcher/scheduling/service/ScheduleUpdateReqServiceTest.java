//package sketcher.scheduling.service;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import sketcher.scheduling.domain.ManagerAssignSchedule;
//import sketcher.scheduling.domain.Schedule;
//import sketcher.scheduling.domain.ScheduleUpdateReq;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.ManagerAssignScheduleDto;
//import sketcher.scheduling.dto.ScheduleDto;
//import sketcher.scheduling.dto.ScheduleUpdateReqDto;
//import sketcher.scheduling.dto.UserDto;
//import sketcher.scheduling.repository.ScheduleRepository;
//import sketcher.scheduling.repository.ScheduleUpdateReqRepository;
//import sketcher.scheduling.repository.UserRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Rollback(value = false)
//public class ScheduleUpdateReqServiceTest {
//
//    @Autowired
//    ScheduleService scheduleService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    ScheduleUpdateReqService updateReqService;
//    @Autowired
//    ManagerAssignScheduleService assignScheduleService;
//
//    @Transactional
//    @Test
//    public void savedUpdateRequestTest() {
//        //given - 연관된 데이터 생성
//
//        //유저 생성
//        UserDto user = UserDto.builder()
//                .id("min")
//                .authRole("user")
//                .password("1234")
//                .username("정민환")
//                .userTel("1234-5678")
//                .build();
//
//        String userId = userService.saveUser(user);
//        Optional<User> userOptional = userService.findById(userId);
//
//        //스케줄 모델 생성
//        ScheduleDto schedule = ScheduleDto.builder()
//                .scheduleDateTimeStart(LocalDateTime.now())
//                .scheduleDateTimeEnd(LocalDateTime.now())
//                .workforce(10)
//                .expected_card_cnt(50)
//                .creator_id(userId)
//                .update_id(userId)
//                .create_date(LocalDateTime.now())
//                .update_date(LocalDateTime.now())
//                .build();
//
//        Integer scheduleId = scheduleService.saveSchedule(schedule);
//        Optional<Schedule> scheduleOptional = scheduleService.findById(scheduleId);
//
//        //스케줄 배정
//
//        ManagerAssignScheduleDto assignSchedule = ManagerAssignScheduleDto.builder()
//                .user(userOptional.get())
//                .schedule(scheduleOptional.get())
//                .build();
//        Integer assignedId = assignScheduleService.saveManagerAssignSchedule(assignSchedule);
////        Optional<ManagerAssignSchedule> assignedSchedule = assignScheduleService.findById(assignedId);
//
//        //when - 스케줄 수정 요청
//
//        ScheduleUpdateReqDto updateReq = ScheduleUpdateReqDto.builder()
//                .scheduleId(assignedId)
//                .changeDate(/*변경시간*/)
//                .build();
//        Integer updateReqSch = updateReqService.saveScheduleUpdateReq(updateReq);
//
//        //then - 스케줄 수정 요청 리스트 조회
//        Assertions.assertEquals(updateReqService.findAll().size(), 1);
//    }
//
//
//}