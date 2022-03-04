package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;

import java.util.ArrayList;
import java.util.List;

public interface UserRepositoryCustom {
    Page<UserDto> findAllManager(UserSearchCondition condition, Pageable pageable);

    ArrayList<String> findDetailById(String id);

    List<User> withdrawalManagers(UserSearchCondition condition);
}
