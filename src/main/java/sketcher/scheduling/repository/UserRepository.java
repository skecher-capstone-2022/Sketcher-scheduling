package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(String id);

    List<User> findAll();

    @Query("select u from User u where u.authRole = 'MANAGER'")
    List<User> findAllManager();

	@Query("select u from User u where u.id = :userid")
    List<User> idCheck(@Param("userid") String userid);

	Optional<User> findByCode(int code);

	@Query("select u from User u where u.dropoutReqCheck='Y'")
    List<User> dropoutUserList();

    @Query("select u from User u where u.authRole='MANAGER'")
    List<User> findAuthRoleManager();
}