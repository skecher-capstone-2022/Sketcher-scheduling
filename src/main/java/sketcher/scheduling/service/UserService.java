package sketcher.scheduling.service;


import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.repository.UserRepositoryCustom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;

	private Optional<User> findByCode(int code) {
        return userRepository.findByCode(code);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

	@Transactional(readOnly = true)
    public ArrayList<String> findDetailById(String id) {
        return userRepositoryCustom.findDetailById(id);
    }

    @Transactional(readOnly = true)
    public Page<User> findAllManager(UserSearchCondition condition, Pageable pageable) {
        return userRepositoryCustom.findAllManager(condition, pageable);
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
//    spring security 인증 과정
//    1. 유저 세션 생성
//    2. 반환된 User객체를 시큐리티 컨텍스트 폴더에 저장
//       (시큐리티 컨텍스트 폴더 : 스프링시큐리티의 인메모리 저장소)
//    3. 유저 세션ID와 함께 응답을 보냄
//    4. 이후 요청 쿠키에서 JSESSION ID를 검증
//    5. JSESSION ID가 유효하다면 인증


    //아이디 중복 검사
    public boolean userIdCheck(String user_id) {
        return userRepository.idCheck(user_id).isEmpty();
        //true : 아이디가 존재하지 않을 때
        //false : 아이디가 이미 존재할 때
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
