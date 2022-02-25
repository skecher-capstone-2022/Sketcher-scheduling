package sketcher.scheduling;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerWorkingSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.ManagerWorkingScheduleDto;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Component
@Transactional
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {


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
    ManagerWorkingScheduleService managerWorkingScheduleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LocalDateTime date1 = LocalDateTime.of(2022,2,19,1,00);
        LocalDateTime date2 = LocalDateTime.of(2022,2,19,4,00);
        LocalDateTime date3 = LocalDateTime.of(2022,2,19,20,00);
        LocalDateTime date4 = LocalDateTime.of(2022,2,19,23,00);
        LocalDateTime date5 = LocalDateTime.of(2022,2,20,17,00);
        LocalDateTime date6 = LocalDateTime.of(2022,2,20,22,00);
        LocalDateTime date7 = LocalDateTime.of(2022,2,21,7,00);
        LocalDateTime date8 = LocalDateTime.of(2022,2,21,18,00);

        UserDto userA = UserDto.builder()
                .id("user1")
                .authRole("MANAGER")
                .password("1234")
                .username("정민환")
                .userTel("010-1234-5678")
                .managerScore(5.0)
                .user_joinDate(date1)
                .build();
        String user1 = userService.saveUser(userA);
//        User userJ = userRepository.findByUsername(user1).get();

        UserDto userB = UserDto.builder()
                .id("user2")
                .authRole("MANAGER")
                .password("1234")
                .username("박태영")
                .userTel("010-1234-5678")
                .user_joinDate(date2)
                .managerScore(5.0)
                .build();
        String user2 = userService.saveUser(userB);
//        User userT = userRepository.findByUsername(user2).get();

        UserDto userC = UserDto.builder()
                .id("user3")
                .authRole("MANAGER")
                .password("1234")
                .username("이혜원")
                .userTel("010-1234-5678")
                .user_joinDate(date3)
                .managerScore(5.0)
                .build();
        String user3 = userService.saveUser(userC);
//        User userL = userRepository.findByUsername(user3).get();

        ManagerHopeTimeDto hope1 = ManagerHopeTimeDto.builder()
                .user(userA.toEntity())
                .start_time(12)
                .finish_time(18)
                .build();

        managerHopeTimeService.saveManagerHopeTime(hope1);

        ManagerHopeTimeDto hope2 = ManagerHopeTimeDto.builder()
                .user(userB.toEntity())
                .start_time(18)
                .finish_time(24)
                .build();

        managerHopeTimeService.saveManagerHopeTime(hope2);

        ManagerHopeTimeDto hope3 = ManagerHopeTimeDto.builder()
                .user(userC.toEntity())
                .start_time(6)
                .finish_time(12)
                .build();

        managerHopeTimeService.saveManagerHopeTime(hope3);

        ScheduleDto scheduleDto1 = ScheduleDto.builder()
                .scheduleDateTimeStart(date1)
                .scheduleDateTimeEnd(date2)
                .build();

        ScheduleDto scheduleDto2 = ScheduleDto.builder()
                .scheduleDateTimeStart(date3)
                .scheduleDateTimeEnd(date4)
                .build();

        ScheduleDto scheduleDto3 = ScheduleDto.builder()
                .scheduleDateTimeStart(date5)
                .scheduleDateTimeEnd(date6)
                .build();

        ScheduleDto scheduleDto4 = ScheduleDto.builder()
                .scheduleDateTimeStart(date7)
                .scheduleDateTimeEnd(date8)
                .build();

        scheduleService.saveSchedule(scheduleDto1);
        scheduleService.saveSchedule(scheduleDto2);
        scheduleService.saveSchedule(scheduleDto3);
        scheduleService.saveSchedule(scheduleDto4);

//        ManagerWorkingScheduleDto managerWorkingScheduleDto1 = ManagerWorkingScheduleDto.builder()
//                .user(userA.toEntity())
//                .schedule(scheduleDto1.toEntity())
//                .build();
//
//        ManagerWorkingScheduleDto managerWorkingScheduleDto2 = ManagerWorkingScheduleDto.builder()
//                .user(userB.toEntity())
//                .schedule(scheduleDto2.toEntity())
//                .build();
//
//        ManagerWorkingScheduleDto managerWorkingScheduleDto3 = ManagerWorkingScheduleDto.builder()
//                .user(userC.toEntity())
//                .schedule(scheduleDto3.toEntity())
//                .build();
//
//        ManagerWorkingScheduleDto managerWorkingScheduleDto4 = ManagerWorkingScheduleDto.builder()
//                .user(userC.toEntity())
//                .schedule(scheduleDto4.toEntity())
//                .build();
//
//        managerWorkingScheduleService.saveManagerWorkingSchedule(managerWorkingScheduleDto1);
//        managerWorkingScheduleService.saveManagerWorkingSchedule(managerWorkingScheduleDto2);
//        managerWorkingScheduleService.saveManagerWorkingSchedule(managerWorkingScheduleDto3);
//        managerWorkingScheduleService.saveManagerWorkingSchedule(managerWorkingScheduleDto4);

//        ScheduleDto schedule1 = setScheduleDto(date1);
//        ScheduleDto schedule2 = setScheduleDto(date2);
//        ScheduleDto schedule3 = setScheduleDto(date3);
//        ScheduleDto schedule4 = setScheduleDto(date4);
//        //when
//        scheduleService.saveSchedule(schedule1);


    }

}
