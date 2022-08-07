package sketcher.scheduling.createSchedule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class EstimatedNumOfCardsTest {
    public final Integer TIME_LEN = 20;

    @Autowired
    EstimatedNumOfCardsPerHourRepository repository;

    @Test
    public void 객체_생성_확인(){
        //given
        int[] time = {0,1,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        int[] value = {65,60,93,130,195,265,222,183,289,300,181,136,124,200,271,294,178,155,89,62};


        //when
        for (int i = 0; i < TIME_LEN; i++) {
            EstimatedNumOfCardsPerHour num = new EstimatedNumOfCardsPerHour(time[i],value[i]);
            repository.save(num);
        }

        //then
        Assert.assertEquals(repository.findAll().size(), 20);
    }
}
