package sketcher.scheduling.createCalendar;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;
import sketcher.scheduling.service.createSchedule.CreateTmpScheduleService;
import sketcher.scheduling.service.createSchedule.SaveAssginScheduleService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    SaveAssginScheduleService saveScheduleService;


    @Test
    public void firstCreateTmpSchdule() {

        //given
        String cellId = "m0801";
        LocalDate date = LocalDate.now();
        int time = 8;
        User user = new User(1, "user1", "MANAGER", "12345", "박태영", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');

        TmpManager tmpManager = new TmpManager(user, new int[]{1, 0, 1, 1});

        //when
        TmpSchedule tmpSchedule = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager);

        //then
        System.out.println(tmpSchedule.getCellId());
        System.out.println(tmpSchedule.getTime());
        System.out.println(tmpSchedule.getDate());
        System.out.println(tmpSchedule.getTmpManager().getUser().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    }

    @Test
    public void updateTmpSchdule() {
        //given
        String cellId = "m0801";
        LocalDate date = LocalDate.now();
        int time = 8;
        User user1 = new User(1, "user1", "MANAGER", "12345", "박태영", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');
        User user2 = new User(2, "user2", "ADMIN", "12345", "이혜원", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');


        TmpManager tmpManager1 = new TmpManager(user1, new int[]{1, 0, 1, 1});
        TmpSchedule tmpSchedule1 = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager1);


        //when

        TmpManager tmpManager2 = new TmpManager(user2, new int[]{1, 0, 1, 1});
        TmpSchedule tmpSchedule2 = tmpScheduleService.updateTmpObject(cellId, date, time, tmpManager2);


        //then
        Assertions.assertEquals(tmpScheduleService.getTmpScheduleList().size(), 1);

    }

    @Test
    public void saveAssignSchedule() {
        //given
        String cellId1 = "m0801";
        String cellId2 = "m0901";
        String cellId3 = "m1001";
        String cellId4 = "m1101";

        LocalDate date = LocalDate.now();
        User user1 = new User(1, "user1", "MANAGER", "12345", "박태영", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');
        User user2 = new User(2, "user2", "ADMIN", "12345", "이혜원", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');
        User user3 = new User(3, "user3", "ADMIN", "12345", "정민환", "010-1234-5678", LocalDateTime.now(), 0.0, 'N');
        User user4 = new User(4, "user4", "ADMIN", "12345", "김희수", "10-1234-5678", LocalDateTime.now(), 0.0, 'N');


        TmpManager tmpManager1 = new TmpManager(user1, new int[]{1, 0, 1, 1});
        tmpScheduleService.updateTmpObject(cellId1, date, 8, tmpManager1);
        TmpManager tmpManager2 = new TmpManager(user2, new int[]{1, 0, 1, 1});
        tmpScheduleService.updateTmpObject(cellId2, date, 9, tmpManager2);
        TmpManager tmpManager3 = new TmpManager(user3, new int[]{1, 0, 1, 1});
        tmpScheduleService.updateTmpObject(cellId3, date, 10, tmpManager3);
        TmpManager tmpManager4 = new TmpManager(user4, new int[]{1, 0, 1, 1});
        tmpScheduleService.updateTmpObject(cellId4, date, 11, tmpManager4);


        System.out.println();
        Assertions.assertEquals(tmpScheduleService.getTmpScheduleList().size(), 4);
        //when

        saveScheduleService.saveAssignSchedule();

        //then
        Assertions.assertEquals(tmpScheduleService.getTmpScheduleList().size(), 0);

    }





}