package sketcher.scheduling.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.repository.UserRepositoryCustom;
import sketcher.scheduling.repository.UserRepositoryCustomImpl;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;

    @Transactional(readOnly = true)
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ManagerHopeTime> findDetailById(String id) {
        return userRepository.findDetailById(id);
    }


    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> findAllManager(UserSearchCondition condition, Pageable pageable) {
        return userRepositoryCustom.findAllManager(condition, pageable);
    }

    /**
     * 저장하고 id 값 리턴
     */
    @Transactional
    public String saveUser(UserDto user){
        return userRepository.save(user.toEntity()).getUsername();
    }
}
