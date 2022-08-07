package sketcher.scheduling.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

import java.util.List;
import java.util.Optional;

public interface ManagerHopeTimeRepository extends JpaRepository<ManagerHopeTime , Integer> {
    List<ManagerHopeTime> findAll();
    List<ManagerHopeTime> findManagerHopeTimeByUser(User user);
	Optional<ManagerHopeTime> findById(Integer id);

	@Modifying
    @Query("update ManagerHopeTime h set h.user=null where h.user=:user")
    int bulkUserSetNull(@Param("user") User user);

    @Query(value = "select h from ManagerHopeTime h where "+
            "h.user.code = :userCode")
    List<ManagerHopeTime> getHopeTimeByUserCode(@Param("userCode") Integer userCode);
}
