package sketcher.scheduling.updateReqTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(false)
public class scheduleUpdateReqTest {
    @Autowired
    EntityManager em;

    @Autowired
    ManagerAssignScheduleService scheduleService;

    @Autowired
    ScheduleUpdateReqService updateReqService;


    public ManagerAssignSchedule setUpOriginalSchedule(){
        ManagerAssignSchedule originalSchedule = scheduleService.findById(1).get(); //2022-05-14 오전 8시
        return originalSchedule;
    }

    public ScheduleUpdateReq setUpUpdateReq(){
        //given
        ManagerAssignSchedule managerAssignSchedule = setUpOriginalSchedule();
        LocalDateTime changeStartDate = LocalDateTime.of(2022, 5, 20, 9, 30);
        LocalDateTime changeEndDate = LocalDateTime.of(2022, 5, 20, 11, 0);

        //when
        Integer savedId = updateReqService.saveUpdateReq(managerAssignSchedule, changeStartDate, changeEndDate);
        ScheduleUpdateReq scheduleUpdateReq = updateReqService.findById(savedId).get();

        //then
        return scheduleUpdateReq;

    }

    @Test
    public void assignSchedule에_reqId_업데이트(){
        //given
        ManagerAssignSchedule managerAssignSchedule = setUpOriginalSchedule();
        ScheduleUpdateReq scheduleUpdateReq = setUpUpdateReq();
        //when
        managerAssignSchedule.updateReqId(scheduleUpdateReq);

        //then
        Assertions.assertEquals(managerAssignSchedule.getUpdateReq(),scheduleUpdateReq);
    }


}

/* TO DO LIST */
//TODO 1 : 유저가 스케줄 변경을 요청하는 경우
    //TODO 1-1 : 일대일매핑 확인 -> 일대일매핑으로 연결되어있는데 assignSchedule을 꼭 updateReq에서 관리해야하나?
    //1-2 : 'SCHEDULE_UPDATE_REQ'테이블의 CHANGE_START_DATE, CHANGE_END_DATE 칼럼 수정 후 객체 생성 확인
    //1-3 : 'MANAGER_ASSIGN_SCHEDULE'테이블의 UPDATE_REQ_ID 컬럼에 updateReqId 저장 확인
    //TODO 1-4 : view 출력 확인
    //TODO 1-5 : 리팩토링
//TODO 유저가 스케줄 변경을 재요청하는 경우
//TODO 관리자가 변경 요청을 수락하는 경우
//TODO 관리자가 변경 요청을 거절하는 경우


