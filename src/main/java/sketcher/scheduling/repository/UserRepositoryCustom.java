package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserSearchCondition;

import java.util.List;

public interface UserRepositoryCustom {
    Page<User> findAllManager(UserSearchCondition condition, Pageable pageable);

    List<ManagerHopeTime> findDetailById(String id);
}
