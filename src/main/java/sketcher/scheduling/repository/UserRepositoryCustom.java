package sketcher.scheduling.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;

import java.util.List;

public interface UserRepositoryCustom {
//    List<User> search(UserSearchCondition condition);

    //    List<User> search(UserSearchCondition condition);
    Page<User> searchPageSimple(UserSearchCondition condition, Pageable pageable);
    Page<User> searchPageComplex(UserSearchCondition condition, Pageable pageable);
}
