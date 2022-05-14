package sketcher.scheduling.createCalendar;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.UserService;
import sketcher.scheduling.service.createSchedule.CreateTmpManagerService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
//import static sketcher.scheduling.domain.QUser.user;

//import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(false)
public class TmpManagerTest {

    @Autowired
    EntityManager em;

    @Autowired
    CreateTmpManagerService tmpManagerService;

    @Autowired
    UserService userService;

    @Test
    public void createDummyUser() {
        for (int i = 1; i <= 50; i++) {
            UserDto user = UserDto.builder()
                    .id("user" + i)
                    .authRole("MANAGER")
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .username("테스트" + i)
                    .userTel("010-1234-5678")
                    .user_joinDate(LocalDateTime.now())
                    .managerScore(3.5)
                    .dropoutReqCheck('N')
                    .build();
            userService.saveUser(user);
        }

    }

    @Test
    public void createTmpManager() {

        long startTime = System.currentTimeMillis();

        //given
        tmpManagerService.createTmpObject();
        List<TmpManager> tmpObjects = tmpManagerService.getTmpManagerList();

        //when

        //then
//        Assertions.assertEquals(createTmpManager.getWeekend_time(),1);

        int i = 0;
        for (TmpManager tmpManager : tmpObjects) {
            i++;
            System.out.print("[ " + i + " ] " + tmpManager.getUser().getId() + " : "
                    + tmpManager.getUser().getUsername() + " (hopetime : ");
            for (int j : tmpManager.getHopeTime()) {
                System.out.print(j + ",");
            }
            System.out.println(")");
        }

        long endTime = System.currentTimeMillis();

        System.out.println(String.format("코드 실행 시간: %20dms", endTime - startTime));
    }


}