package sketcher.scheduling.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.dto.ScheduleDto;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Test
    void 스케줄_생성_테스트() {
        //given

        Date date = new Date();

        ScheduleDto scheduleDto = ScheduleDto.builder()
                .scheduleDate(date)
                .scheduleTime(2)
                .workforce(2)
                .expected_card_cnt(100)
                .build();

        //when
        scheduleService.saveSchedule(scheduleDto);
        //then
        scheduleService.findAll();
    }

}