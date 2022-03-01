package sketcher.scheduling.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.ScheduleUpdateReqRepository;
import sketcher.scheduling.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
class ScheduleUpdateReqServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleUpdateReqRepository updateReqRepository;

    @Transactional
    @Test
    public void 수정요청저장(){
        //given
        UserDto user = UserDto.builder()
                .id("min")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();

        String userId = userService.saveUser(user);

        ScheduleDto schedule = ScheduleDto.builder()
                .scheduleDateTimeStart(LocalDateTime.now())
                .scheduleDateTimeEnd(LocalDateTime.now())
                .workforce(10)
                .expected_card_cnt(50)
                .creator_id(userId)
                .update_id(userId)
                .create_date(LocalDateTime.now())
                .update_date(LocalDateTime.now())
                .build();

        Integer scheduleId = scheduleService.saveSchedule(schedule);



        //when

        //then
    }


}