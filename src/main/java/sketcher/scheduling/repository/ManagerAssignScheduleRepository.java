package sketcher.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ManagerAssignScheduleRepository extends JpaRepository<ManagerAssignSchedule, Integer> {


//    List<ManagerAssignSchedule> AssignScheduleFindAll();
//@Query(value = "select s from ManagerAssignSchedule s"+
//                " join fetch s.updateReq u")
//    List<ManagerAssignSchedule> findScheduleAll();
    Optional<ManagerAssignSchedule> findById(Integer id);
    List<ManagerAssignSchedule> findByUser(User user);
    @Query(value ="select s from ManagerAssignSchedule s where " +
            "s.scheduleDateTimeStart = :startDate and s.user = :user and s.scheduleDateTimeEnd = :endDate")
    Optional<ManagerAssignSchedule> getBeforeSchedule(@Param("user")User user,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);
    Integer deleteByUser(User user);
    void deleteById(Integer Id);

    @Modifying
    @Query("update ManagerAssignSchedule a set a.user=null where a.user=:user")
    int bulkUserSetNull(@Param("user") User user);
}
