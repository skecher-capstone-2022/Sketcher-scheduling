package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserSearchCondition;

public interface UserRepositoryCustom {
    Page<User> findAllManager(UserSearchCondition condition, Pageable pageable);
}
