package sketcher.scheduling.calendarTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalendarControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    ManagerAssignScheduleService managerAssignScheduleService;


    @Test
    @Transactional
    public void showEachEventTest() throws Exception {
        // given
        User user1 = userService.findByUsername("정민환").orElseThrow(() -> new Exception("정민환 매니저가 존재하지 않습니다."));
        User user2 = userService.findByUsername("테스트").orElseThrow(() -> new Exception("테스트 매니저가 존재하지 않습니다."));

        List<ManagerAssignSchedule> managerAssignScheduleList1 = user1.getManagerAssignScheduleList();
        List<ManagerAssignSchedule> findScheduleByUser1 = managerAssignScheduleService.findByUser(user1);

        List<ManagerAssignSchedule> managerAssignScheduleList2 = user2.getManagerAssignScheduleList();
        List<ManagerAssignSchedule> findScheduleByUser2 = managerAssignScheduleService.findByUser(user2);
        // when
        int userSize = managerAssignScheduleList1.size();
        int scheduleSize = findScheduleByUser1.size();

        int testSize = managerAssignScheduleList2.size();
        int testScheduleSize = findScheduleByUser2.size();
        // then
        assertThat(user1.getUsername()).isEqualTo("정민환");
        assertThat(user2.getUsername()).isEqualTo("테스트");

        assertThat(userSize).isEqualTo(scheduleSize);
        assertThat(testSize).isEqualTo(testScheduleSize);

    }

    @Test
    @Transactional
    public void createEventTest() throws Exception {
        // given

        LocalDateTime date1 = LocalDateTime.of(2022,3,10,1,00);
        LocalDateTime date3 = LocalDateTime.of(2022,3,10,20,00);

        UserDto userDto = UserDto.builder()
                .id("testmin")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();

        String testmin = userService.saveUser(userDto);
        User user = userService.findById(testmin).orElseThrow(() -> new Exception("해당 매니저가 없습니다."));
        // when 
        ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                .user(user)
                .scheduleDateTimeStart(date1)
                .scheduleDateTimeEnd(date3)
                .build();
        Integer scheduleId = managerAssignScheduleService.saveManagerAssignSchedule(managerAssignScheduleDto);
        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.findById(scheduleId).orElseThrow(() -> new Exception("해당 스케줄이 존재하지 않습니다."));
        List<ManagerAssignSchedule> testMinSchedule = managerAssignScheduleService.findByUser(user);

        // then
        assertThat(testmin).isEqualTo("testmin");
        assertThat(managerAssignSchedule.getUser().getUsername()).isEqualTo("정민환");
        assertThat(testMinSchedule.size()).isEqualTo(1);
    }
}