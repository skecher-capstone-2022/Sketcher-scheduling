package sketcher.scheduling.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.repository.ScheduleRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @Transactional
    @Commit
    public void 스케줄_생성_테스트() throws Exception {
        //given

        Date date = new Date();
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = date1.parse("2022-02-19");

        ScheduleDto schedule = ScheduleDto.builder()
                .scheduleDate(date2)
                .scheduleTime(2)
                .workforce(2)
                .expected_card_cnt(100)
                .build();

        //when
        Integer id = scheduleService.saveSchedule(schedule);
        Schedule scheduleA = scheduleRepository.findById(id).get();
        //then
        Assertions.assertEquals(2, scheduleA.getWorkforce());
    }

}