package sketcher.scheduling.createSchedule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.domain.PercentageOfManagerWeightsId;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class PercentageOfManagerWeightsTest {

    @Autowired
    PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    @Test
    public void 퍼센트_생성() {
        //given
        PercentageOfManagerWeightsId percentageOfManagerWeightsId = new PercentageOfManagerWeightsId(50, 25, 25);

        //when
        if(percentageOfManagerWeightsId.getHigh() + percentageOfManagerWeightsId.getMiddle() + percentageOfManagerWeightsId.getLow() == 100) {
            PercentageOfManagerWeights percentageOfManagerWeights = new PercentageOfManagerWeights(percentageOfManagerWeightsId);
            percentageOfManagerWeightsRepository.save(percentageOfManagerWeights);
        }

        //then
        assertEquals(percentageOfManagerWeightsRepository.findAll().size(), 1);
    }

}
