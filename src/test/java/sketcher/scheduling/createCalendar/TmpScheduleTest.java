package sketcher.scheduling.createCalendar;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;
import sketcher.scheduling.service.UserService;
import sketcher.scheduling.service.createSchedule.CreateTmpManagerService;
import sketcher.scheduling.service.createSchedule.CreateTmpScheduleService;

import javax.persistence.EntityManager;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
//import static sketcher.scheduling.domain.QUser.user;

//import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(false)
public class TmpScheduleTest {

    @Autowired
    EntityManager em;

    @Autowired
    CreateTmpScheduleService tmpScheduleService;


    @Test
    public void firstCreateTmpSchdule() {

        //given
        String cellId = "m0801";
        LocalDate date = LocalDate.now();
        int time = 8;
        TmpManager tmpManager = new TmpManager(1, "user1",
                                        "테스트1", new int[]{1, 0, 1, 1});

        //when
        TmpSchedule tmpSchedule = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager);

        //then
        System.out.println(tmpSchedule.getCellId());
        System.out.println(tmpSchedule.getTime());
        System.out.println(tmpSchedule.getDate());
        System.out.println(tmpSchedule.getTmpManager().getUserid());

    }

    @Test
    public void updateTmpSchdule() {
        //given
        String cellId = "m0801";
        LocalDate date = LocalDate.now();
        int time = 8;
        TmpManager tmpManager1 = new TmpManager(1, "user1",
                "테스트1", new int[]{1, 0, 1, 1});
        TmpSchedule tmpSchedule1 = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager1);


        //when

        TmpManager tmpManager2 = new TmpManager(1, "user1",
                "테스트1", new int[]{1, 0, 1, 1});
        TmpSchedule tmpSchedule2 = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager2);


        //then
        Assertions.assertEquals(tmpScheduleService.getTmpScheduleList().size(),1);

    }
}