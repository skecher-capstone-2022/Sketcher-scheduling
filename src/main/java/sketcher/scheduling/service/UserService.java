package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * 저장하고 id 값 리턴
     */
    @Transactional
    public String saveUser(UserDto user){
        return userRepository.save(user.toEntity()).getUsername();
    }

}
