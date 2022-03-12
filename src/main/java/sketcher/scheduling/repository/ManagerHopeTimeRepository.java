package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

import java.util.List;
import java.util.Optional;

public interface ManagerHopeTimeRepository extends JpaRepository<ManagerHopeTime , Integer> {
    List<ManagerHopeTime> findAll();
    List<ManagerHopeTime> findManagerHopeTimeByUser(User user);
}
