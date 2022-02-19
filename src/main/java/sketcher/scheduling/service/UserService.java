package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Page<User> findAllManager(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "user_joindate"));

        return userRepository.findAllManager(pageable);
    }

    /**
     * 저장하고 id 값 리턴
     */
    @Transactional
    public String saveUser(UserDto user){
        return userRepository.save(user.toEntity()).getId();
    }

}
