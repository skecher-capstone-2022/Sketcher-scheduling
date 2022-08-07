package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.ManagerAssignSchedule;

import java.util.List;

public interface EstimatedNumOfCardsPerHourRepository extends JpaRepository<EstimatedNumOfCardsPerHour, Integer> {



}
