package sketcher.scheduling.createSchedule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.dto.PercentageOfManagerWeightsDto;
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

        PercentageOfManagerWeightsDto percentageOfManagerWeightsDto = PercentageOfManagerWeightsDto.builder()
                .high(50)
                .middle(25)
                .low(25)
                .build();

        //when
        percentageOfManagerWeightsRepository.save(percentageOfManagerWeightsDto.toEntity());

        //then
        assertEquals(percentageOfManagerWeightsRepository.findAll().size(), 1);
    }

}
