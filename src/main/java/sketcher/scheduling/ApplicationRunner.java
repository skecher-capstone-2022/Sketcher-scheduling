package sketcher.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.*;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.*;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@Transactional
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    EntityManager em;

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ManagerHopeTimeService managerHopeTimeService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ManagerAssignScheduleService managerAssignScheduleService;
    @Autowired
    ScheduleUpdateReqService updateReqService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LocalDateTime date1 = LocalDateTime.of(2022,3,10,1,00);
        LocalDateTime date3 = LocalDateTime.of(2022,3,10,20,00);
        LocalDateTime date5 = LocalDateTime.of(2022,3,10,17,00);
        LocalDateTime date7 = LocalDateTime.of(2022,3,10,7,00);


//        UserDto userA = UserDto.builder()
//                .id("user111111")
//                .authRole("MANAGER")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .username("정민환")
//                .userTel("010-1234-5678")
//                .user_joinDate(LocalDateTime.now())
//                .managerScore(5.0)
//                .build();
//        String user1 = userService.saveUser(userA);
//        User userJ = userRepository.findById(user1).get();
//
//        UserDto userB = UserDto.builder()
//                .id("user22222222")
//                .authRole("MANAGER")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .username("박태영")
//                .userTel("010-1234-5678")
//                .user_joinDate(LocalDateTime.now())
//                .managerScore(5.0)
//                .build();
//        String user2 = userService.saveUser(userB);
//        User userT = userRepository.findById(user2).get();
//
//        UserDto userC = UserDto.builder()
//                .id("user3333333333")
//                .authRole("MANAGER")
//                .password(new BCryptPasswordEncoder().encode("1234"))
//                .username("이혜원")
//                .userTel("010-1234-5678")
//                .user_joinDate(LocalDateTime.now())
//                .managerScore(5.0)
//                .build();
//        String user3 = userService.saveUser(userC);
//        User userL = userRepository.findByUsername(user3).get();
//
//        ScheduleDto scheduleDto1 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date1)
//                .build();
//
//        ScheduleDto scheduleDto2 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date3)
//                .build();
//
//        ScheduleDto scheduleDto3 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date5)
//                .build();
//
//        ScheduleDto scheduleDto4 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date7)
//                .build();
//
//        Integer scheduleIdByA = scheduleService.saveSchedule(scheduleDto1);
//        Integer scheduleIdByB = scheduleService.saveSchedule(scheduleDto2);
//        scheduleService.saveSchedule(scheduleDto3);
//        scheduleService.saveSchedule(scheduleDto4);
//
//        Schedule scheduleA = scheduleService.findById(scheduleIdByA).get();
//        Schedule scheduleB = scheduleService.findById(scheduleIdByA).get();
//
//
//        LocalDateTime date2 = LocalDateTime.of(2022,3,10,4,00);
//        LocalDateTime date4 = LocalDateTime.of(2022,3,10,6,00);
//        LocalDateTime date6 = LocalDateTime.of(2022,3,10,22,00);
//        LocalDateTime date8 = LocalDateTime.of(2022,3,10,23,00);
//
//        LocalDateTime date11 = LocalDateTime.of(2022,3,6,2,00);
//        LocalDateTime date12 = LocalDateTime.of(2022,3,6,4,00);
//        LocalDateTime date13 = LocalDateTime.of(2022,3,7,6,00);
//        LocalDateTime date14 = LocalDateTime.of(2022,3,7,9,00);
//        LocalDateTime date15 = LocalDateTime.of(2022,3,13,13,00);
//        LocalDateTime date16 = LocalDateTime.of(2022,3,13,16,00);
//        LocalDateTime date17 = LocalDateTime.of(2022,3,14,20,00);
//        LocalDateTime date18 = LocalDateTime.of(2022,3,14,22,00);
//
//
////        스케줄 배정
//        ManagerAssignScheduleDto assignSchedule = ManagerAssignScheduleDto.builder()
//                .user(userJ)
//                .scheduleDateTimeStart(date1)
//                .scheduleDateTimeEnd(date3)
//                .build();
//        ManagerAssignScheduleDto assignSchedule2 = ManagerAssignScheduleDto.builder()
//                .user(userT)
//                .scheduleDateTimeStart(date11)
//                .scheduleDateTimeEnd(date12)
//                .build();
//        ManagerAssignScheduleDto assignSchedule3 = ManagerAssignScheduleDto.builder()
//                .user(userT)
//                .scheduleDateTimeStart(date13)
//                .scheduleDateTimeEnd(date14)
//                .build();
//        ManagerAssignScheduleDto assignSchedule4 = ManagerAssignScheduleDto.builder()
//                .user(userT)
//                .scheduleDateTimeStart(date15)
//                .scheduleDateTimeEnd(date16)
//                .build();
//        ManagerAssignScheduleDto assignSchedule5 = ManagerAssignScheduleDto.builder()
//                .user(userT)
//                .scheduleDateTimeStart(date17)
//                .scheduleDateTimeEnd(date18)
//                .build();
//        Integer assignedId = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule);
//        Integer assignedId2 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule2);
//        Integer assignedId3 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule3);
//        Integer assignedId4 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule4);
//        Integer assignedId5 = managerAssignScheduleService.saveManagerAssignSchedule(assignSchedule5);
//        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.findById(assignedId).get();
//        ManagerAssignSchedule managerAssignSchedule2 = managerAssignScheduleService.findById(assignedId2).get();
//
////        희망 변경시간
//        LocalDateTime changeDate = LocalDateTime.of(2033,3,6,12,00);
//
//        ScheduleUpdateReqDto updateReq = ScheduleUpdateReqDto.builder()
//                .assignSchedule(managerAssignSchedule)
//                .changeDate(changeDate)
//                .build();
//        ScheduleUpdateReqDto updateReq2 = ScheduleUpdateReqDto.builder()
//                .assignSchedule(managerAssignSchedule2)
//                .changeDate(changeDate)
//                .build();
//        updateReqService.saveScheduleUpdateReq(updateReq);
//        updateReqService.saveScheduleUpdateReq(updateReq2);
//
//
    }

}