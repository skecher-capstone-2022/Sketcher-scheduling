package sketcher.scheduling.algorithm;

import lombok.RequiredArgsConstructor;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AutoScheduling {
    private final UserService userService;
    private final ManagerHopeTimeService managerHopeTimeService;
    private final EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsPerHourRepository;
    private final PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository

    public ArrayList<ResultScheduling> runAlgorithm(int userCode[], int userCurrentTime[], int userHopeTime[]) {
        List<EstimatedNumOfCardsPerHour> Cards = estimatedNumOfCardsPerHourRepository.findAll();
        List<PercentageOfManagerWeights> managerWeights = percentageOfManagerWeightsRepository.findAll();

        return null;
    }

    public void makeManagerNode(Integer code, Integer hopeStartTime, Integer managerWeight) {
        Manager manager = new Manager();
        manager.setCode(code);

    }

    public void makeManagerWeight(List<PercentageOfManagerWeights> managerWeights) {

    }

}
