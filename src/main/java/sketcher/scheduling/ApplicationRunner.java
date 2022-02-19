package sketcher.scheduling;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.ScheduleService;
import sketcher.scheduling.service.UserService;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //given
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = date.parse("2022-02-19");
//        Date date2 = date.parse("2022-02-19");
//        Date date3 = date.parse("2022-02-20");
//        Date date4 = date.parse("2022-02-21");

//        LocalDate date1 = LocalDate.of(2022,2,19);
//        LocalDate date2 = LocalDate.of(2022,2,19);
//        LocalDate date3 = LocalDate.of(2022,2,20);
//        LocalDate date4 = LocalDate.of(2022,2,21);

//        LocalTime time1 = LocalTime.of(1, 00);
//        LocalTime time2 = LocalTime.of(4, 00);
//        LocalTime time3 = LocalTime.of(20, 00);
//        LocalTime time4 = LocalTime.of(23, 00);
//        LocalTime time5 = LocalTime.of(17, 00);
//        LocalTime time6 = LocalTime.of(22, 00);
//        LocalTime time7 = LocalTime.of(10, 00);
//        LocalTime time8 = LocalTime.of(14, 00);

        LocalDateTime date1 = LocalDateTime.of(2022,2,19,1,00);
        LocalDateTime date2 = LocalDateTime.of(2022,2,19,4,00);
        LocalDateTime date3 = LocalDateTime.of(2022,2,19,20,00);
        LocalDateTime date4 = LocalDateTime.of(2022,2,19,23,00);
        LocalDateTime date5 = LocalDateTime.of(2022,2,20,17,00);
        LocalDateTime date6 = LocalDateTime.of(2022,2,20,22,00);
        LocalDateTime date7 = LocalDateTime.of(2022,2,21,7,00);
        LocalDateTime date8 = LocalDateTime.of(2022,2,21,18,00);

        UserDto userA = UserDto.builder()
                .username("정민환")
                .build();
        String user = userService.saveUser(userA);
        User user1 = userRepository.findById(user).get();

        ManagerHopeTimeDto managerHopeTime1 = ManagerHopeTimeDto.builder()
                .managerHopeDateStart(date1)
                .managerHopeDateEnd(date2)
                .user(user1)
                .build();

        ManagerHopeTimeDto managerHopeTime2 = ManagerHopeTimeDto.builder()
                .managerHopeDateStart(date3)
                .managerHopeDateEnd(date4)
                .user(user1)
                .build();

        ManagerHopeTimeDto managerHopeTime3 = ManagerHopeTimeDto.builder()
                .managerHopeDateStart(date5)
                .managerHopeDateEnd(date6)
                .user(user1)
                .build();

        ManagerHopeTimeDto managerHopeTime4 = ManagerHopeTimeDto.builder()
                .managerHopeDateStart(date7)
                .managerHopeDateEnd(date8)
                .user(user1)
                .build();

        managerHopeTimeService.saveManagerHopeTime(managerHopeTime1);
        managerHopeTimeService.saveManagerHopeTime(managerHopeTime2);
        managerHopeTimeService.saveManagerHopeTime(managerHopeTime3);
        managerHopeTimeService.saveManagerHopeTime(managerHopeTime4);

//        ScheduleDto schedule1 = setScheduleDto(date1);
//        ScheduleDto schedule2 = setScheduleDto(date2);
//        ScheduleDto schedule3 = setScheduleDto(date3);
//        ScheduleDto schedule4 = setScheduleDto(date4);
//        //when
//        scheduleService.saveSchedule(schedule1);


    }

    private ScheduleDto setScheduleDto(Date date1) {
        return ScheduleDto.builder()
                .scheduleDate(date1)
                .scheduleTime(2)
                .workforce(2)
                .expected_card_cnt(100)
                .build();
    }
}
