package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.ManagerAssignSchedule;

import java.util.List;

public interface EstimatedNumOfCardsPerHourRepository extends JpaRepository<EstimatedNumOfCardsPerHour, Integer> {


    @Query("select sum(e.numOfCards)/count(e.numOfCards) from EstimatedNumOfCardsPerHour e")
    public Integer totalCardValueAvg();
}
