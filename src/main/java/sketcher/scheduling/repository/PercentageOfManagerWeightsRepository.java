package sketcher.scheduling.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.PercentageOfManagerWeights;

public interface PercentageOfManagerWeightsRepository extends JpaRepository<PercentageOfManagerWeights, Integer> {

}
