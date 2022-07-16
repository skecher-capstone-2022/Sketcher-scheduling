package sketcher.scheduling.updateReqTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;
import sketcher.scheduling.service.UserService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
//@Rollback(value = false)
public class ScheduleUpdateReqTest {
    @Autowired
    EntityManager em;

    @Autowired
    ManagerAssignScheduleService scheduleService;

    @Autowired
    ScheduleUpdateReqService updateReqService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void 유저가_스케줄_변경을_요청하는_경우() {
        //given
        ManagerAssignSchedule managerAssignSchedule = setUpOriginalSchedule();
        LocalDateTime changeStartDate = LocalDateTime.of(2022, 5, 20, 9, 30);
        LocalDateTime changeEndDate = LocalDateTime.of(2022, 5, 20, 11, 0);

        //when
        Integer savedReqId = updateReqService.saveScheduleUpdateReq(managerAssignSchedule, changeStartDate, changeEndDate);

        //then
        System.out.println(managerAssignSchedule.getUpdateReq().getId());
        Assertions.assertEquals(managerAssignSchedule.getUpdateReq().getId(), savedReqId);
    }

    @Test
    @Transactional
    public void 유저가_같은_스케줄을_다른_날짜로_중복요청하는_경우() {
        //given
        ManagerAssignSchedule assignSchedule = getAssignSchedule(1);
        LocalDateTime changeStartDate = LocalDateTime.of(2022, 6, 30, 9, 30);
        LocalDateTime changeEndDate = LocalDateTime.of(2022, 6, 30, 11, 0);

        //when
        updateReqService.duplicateUpdateRequest(assignSchedule,changeStartDate,changeEndDate);

        //then
        Assertions.assertEquals(assignSchedule.getUpdateReq().getChangeStartDate(), changeStartDate);
        Assertions.assertEquals(assignSchedule.getUpdateReq().getChangeEndDate(), changeEndDate);

    }

    @Transactional
    @Test
    public void 수정_요청을_수락하는_경우(){
        //given
        Integer updateReqId = 1;

        //when
        updateReqService.acceptReq(updateReqId);

        //then
        ScheduleUpdateReq scheduleUpdateReq = updateReqService.findById(1).get();
        Assertions.assertEquals(scheduleUpdateReq.getReqAcceptCheck(), 'Y');
        Assertions.assertEquals(scheduleUpdateReq.getAssignSchedule().getScheduleDateTimeStart(), LocalDateTime.of(2022, 6, 30, 9, 30));
        Assertions.assertEquals(scheduleUpdateReq.getAssignSchedule().getScheduleDateTimeEnd(), LocalDateTime.of(2022, 6, 30, 11, 0));
    }



    public User setUpUser() {
        UserDto user = UserDto.builder()
                .id("tae")
                .authRole("user")
                .password("1234")
                .username("박태영")
                .userTel("1234-5678")
                .build();

        userService.saveUser(user);

        return userRepository.findById(user.getId()).get();
    }

    @Transactional
    public ManagerAssignSchedule setUpOriginalSchedule() {
        Integer savedId = createAssignSchedule();
        return getAssignSchedule(savedId);
    }

    private ManagerAssignSchedule getAssignSchedule(Integer savedId) {
        return scheduleService.findById(savedId).get();
    }

    private Integer createAssignSchedule() {
        LocalDateTime scheduleDateTimeStart = LocalDateTime.of(2022, 5, 15, 9, 30);
        LocalDateTime scheduleDateTimeEnd = LocalDateTime.of(2022, 5, 15, 11, 0);
        ManagerAssignScheduleDto assignScheduleDto = ManagerAssignScheduleDto.builder()
                .user(setUpUser())
                .scheduleDateTimeStart(scheduleDateTimeStart)
                .scheduleDateTimeEnd(scheduleDateTimeEnd)
                .build();
        return scheduleService.saveManagerAssignSchedule(assignScheduleDto);
    }
}

/* TO DO LIST */
//TODO 관리자가 변경 요청을 수락하는 경우
//TODO 관리자가 변경 요청을 거절하는 경우


