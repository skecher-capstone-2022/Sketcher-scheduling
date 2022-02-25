//package sketcher.scheduling;
//
//import org.apache.tomcat.jni.Local;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.ManagerHopeTimeDto;
//import sketcher.scheduling.dto.ScheduleDto;
//import sketcher.scheduling.dto.UserDto;
//import sketcher.scheduling.repository.ScheduleRepository;
//import sketcher.scheduling.repository.UserRepository;
//import sketcher.scheduling.service.ManagerAssignScheduleService;
//import sketcher.scheduling.service.ManagerHopeTimeService;
//import sketcher.scheduling.service.ScheduleService;
//import sketcher.scheduling.service.UserService;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Date;
//
//@Component
//@Transactional
//public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
//
//
//    @Autowired
//    ScheduleService scheduleService;
//    @Autowired
//    ManagerHopeTimeService managerHopeTimeService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ManagerAssignScheduleService managerAssignScheduleService;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        LocalDateTime date1 = LocalDateTime.of(2022,2,19,1,00);
//        LocalDateTime date2 = LocalDateTime.of(2022,2,19,4,00);
//        LocalDateTime date3 = LocalDateTime.of(2022,2,19,20,00);
//        LocalDateTime date4 = LocalDateTime.of(2022,2,19,23,00);
//        LocalDateTime date5 = LocalDateTime.of(2022,2,20,17,00);
//        LocalDateTime date6 = LocalDateTime.of(2022,2,20,22,00);
//        LocalDateTime date7 = LocalDateTime.of(2022,2,21,7,00);
//        LocalDateTime date8 = LocalDateTime.of(2022,2,21,18,00);
//
//        UserDto userA = UserDto.builder()
//                .username("정민환")
//                .build();
//        String user1 = userService.saveUser(userA);
//        User userJ = userRepository.findByUsername(user1).get();
//
//        UserDto userB = UserDto.builder()
//                .username("박태영")
//                .build();
//        String user2 = userService.saveUser(userB);
//        User userT = userRepository.findByUsername(user2).get();
//
//        UserDto userC = UserDto.builder()
//                .username("이혜원")
//                .build();
//        String user3 = userService.saveUser(userC);
//        User userL = userRepository.findByUsername(user3).get();
//
//        ScheduleDto scheduleDto1 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date1)
//                .scheduleDateTimeEnd(date2)
//                .build();
//
//        ScheduleDto scheduleDto2 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date3)
//                .scheduleDateTimeEnd(date4)
//                .build();
//
//        ScheduleDto scheduleDto3 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date5)
//                .scheduleDateTimeEnd(date6)
//                .build();
//
//        ScheduleDto scheduleDto4 = ScheduleDto.builder()
//                .scheduleDateTimeStart(date7)
//                .scheduleDateTimeEnd(date8)
//                .build();
//
//        scheduleService.saveSchedule(scheduleDto1);
//        scheduleService.saveSchedule(scheduleDto2);
//        scheduleService.saveSchedule(scheduleDto3);
//        scheduleService.saveSchedule(scheduleDto4);
//
//
////        ScheduleDto schedule1 = setScheduleDto(date1);
////        ScheduleDto schedule2 = setScheduleDto(date2);
////        ScheduleDto schedule3 = setScheduleDto(date3);
////        ScheduleDto schedule4 = setScheduleDto(date4);
////        //when
////        scheduleService.saveSchedule(schedule1);
//
//
//    }
//
//}
