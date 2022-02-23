package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , String> { //, UserRepositoryCustom


    Optional<User> findById(String id);
    List<User> findAll();

    @Query("select h from ManagerHopeTime h join h.user u where h.user.id = u.id and u.id = :id order by h.start_time")
    List<ManagerHopeTime> findDetailById(@Param("id") String id);
//    Optional<User> findDetailById(String id);

    @Query("select u from User u where u.authRole = 'MANAGER' and :type like %:keyword%")
    Page<User> findAllManager(String type, String keyword, Pageable pageable); //String searchType,
}
