package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 저장하고 id 값 리턴
     * (회원정보 저장 시, 비밀번호 값을 그대로 DB에 넣는게 아니라
     * 인코딩 처리한 값을 넣게 됨 -> 사용할 때는 그 값을 디코딩해서 사용)
     */
    @Transactional
    public String saveUser(UserDto user) {
        //패스워드 인코딩
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user.toEntity()).getId();
    }

    //아이디로 유저 검색
    @Override  //반환값 다운캐스팅 (UserDetails->User)
    public User loadUserByUsername(String userid) throws UsernameNotFoundException {
        return userRepository.findById(userid).orElseThrow(() -> new UsernameNotFoundException(userid));
    }


    //아이디 중복 검사
    public boolean userIdCheck(String user_id) {
        return userRepository.idCheck(user_id).isEmpty();
        //true : 아이디가 존재하지 않을 때
        //false : 아이디가 이미 존재할 때
    }
}
