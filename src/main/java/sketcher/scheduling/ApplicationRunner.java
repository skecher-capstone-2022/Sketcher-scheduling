package sketcher.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.service.ScheduleService;

import java.util.Date;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {


    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //given
        Date date = new Date();

        ScheduleDto schedule = ScheduleDto.builder()
                .scheduleDate(date)
                .scheduleTime(2)
                .workforce(2)
                .expected_card_cnt(100)
                .build();

        //when
        Integer id = scheduleService.saveSchedule(schedule);
    }
}
