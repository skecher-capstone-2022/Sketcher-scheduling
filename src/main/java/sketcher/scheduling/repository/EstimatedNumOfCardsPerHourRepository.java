package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.ManagerAssignSchedule;

public interface EstimatedNumOfCardsPerHourRepository extends JpaRepository<EstimatedNumOfCardsPerHour, Integer> {

}
