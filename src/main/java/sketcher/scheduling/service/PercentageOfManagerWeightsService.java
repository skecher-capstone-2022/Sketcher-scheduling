package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.dto.PercentageOfManagerWeightsDto;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PercentageOfManagerWeightsService {

    private final PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    public void savePercentageOfManagerWeights(PercentageOfManagerWeightsDto percentageDto) {
        percentageOfManagerWeightsRepository.save(percentageDto.toEntity());
    }

    @Transactional
    public void updatePercentageOfManagerWeights(PercentageOfManagerWeightsDto percentageDto) {
        System.out.println("id : " + percentageDto.getId());


        Optional<PercentageOfManagerWeights> weightsOptional = percentageOfManagerWeightsRepository.findById(percentageDto.getId());

        if (weightsOptional.isPresent()) {
            PercentageOfManagerWeights percentageOfManagerWeights = weightsOptional.get();
            percentageOfManagerWeights.update(percentageDto.getHigh(), percentageDto.getMiddle(), percentageDto.getLow());

        }

    }

}
