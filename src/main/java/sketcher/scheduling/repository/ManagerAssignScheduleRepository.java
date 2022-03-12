package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ManagerAssignScheduleRepository extends JpaRepository<ManagerAssignSchedule, Integer> {
    List<ManagerAssignSchedule> findAll();
    Optional<ManagerAssignSchedule> findById(Integer id);
    List<ManagerAssignSchedule> findByUser(User user);
    Optional<ManagerAssignSchedule> findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(User user, LocalDateTime startDate, LocalDateTime endDate);
    Integer deleteByUser(User user);
    void deleteById(Integer Id);

    @Modifying
    @Query("update ManagerAssignSchedule a set a.user=null where a.user=:user")
    int bulkUserSetNull(@Param("user") User user);
}
