package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface ManagerAssignScheduleRepository extends JpaRepository<ManagerAssignSchedule, Integer> {

    List<ManagerAssignSchedule> findAll();
    List<ManagerAssignSchedule> findByUser(User user);
    String deleteByUser(String username);
}
