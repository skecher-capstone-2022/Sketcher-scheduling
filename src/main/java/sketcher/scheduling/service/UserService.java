package sketcher.scheduling.service;


import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ManagerHopeTimeRepository;
import sketcher.scheduling.repository.ManagerHopeTimeRepositoryCustomImpl;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.repository.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final ManagerHopeTimeRepositoryCustomImpl managerHopeTimeRepositoryCustom;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllManager() {
        return userRepository.findAllManager();
    }

    public List<Tuple> findJoinDateByHopeTime(Integer startTime) {
        return userRepositoryCustom.findJoinDateByHopeTime(startTime);
    }

    public Optional<User> findByCode(int code) {
        return userRepository.findByCode(code);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    //	@Transactional(readOnly = true)
//    public ArrayList<String> findDetailById(String id) {
//        return userRepositoryCustom.findDetailById(id);
//    }
    @Transactional(readOnly = true)
    public ArrayList<String> findHopeTimeById(String id) {
        return managerHopeTimeRepositoryCustom.findHopeTimeById(id);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllManager(UserSearchCondition condition, Pageable pageable) {
        return userRepositoryCustom.findAllManager(condition, pageable);
    }

    @Transactional(readOnly = true)
    public List<User> withdrawalManagers(UserSearchCondition condition) {
        return userRepositoryCustom.withdrawalManagers(condition);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findWorkManager(UserSearchCondition condition, Pageable pageable) {
        return userRepositoryCustom.findWorkManager(condition, pageable);
    }

    @Transactional(readOnly = true)
    public long countByTodayWorkManager() {
        return userRepositoryCustom.countByTodayWorkManager();
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
//        user.setUser_joinDate(LocalDateTime.now());
        user.setManagerScore(0.0);
        user.setDropoutReqCheck('N');
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

    //탈퇴요청리스트
    public List<User> dropoutUserList() {
        return userRepository.dropoutUserList();
    }

    private final ManagerAssignScheduleRepository assignScheduleRepository;
    private final ManagerHopeTimeRepository hopeTimeRepository;

    //유저삭제
    @Transactional
    public void userSetNull(User user) {
        //1. 배정스케줄 및 희망스케줄 연결관계 삭제 (NULL로 처리)
        assignScheduleRepository.bulkUserSetNull(user);
        hopeTimeRepository.bulkUserSetNull(user);
        //2. 유저 삭제
        deleteUser(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void updateUserTel(User user, String userTel) {
        UserDto userDto = UserDto.builder()
                .code(user.getCode())
                .id(user.getId())
                .authRole(user.getAuthRole())
                .password(user.getPassword())
                .username(user.getUsername())
                .userTel(userTel)
                .user_joinDate(user.getUser_joinDate())
                .managerScore(user.getManagerScore())
                .dropoutReqCheck(user.getDropoutReqCheck())
                .build();

        updateUser(userDto);
        // 여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기때문에 세션값 갱신이 필요함
    }

    @Transactional
    public void updateDropoutReqCheck(User user) {
        UserDto userDto = UserDto.builder()
                .code(user.getCode())
                .id(user.getId())
                .authRole(user.getAuthRole())
                .password(user.getPassword())
                .username(user.getUsername())
                .userTel(user.getUserTel())
                .user_joinDate(user.getUser_joinDate())
                .managerScore(user.getManagerScore())
                .dropoutReqCheck('Y')
                .build();

        updateUser(userDto);
    }

    @Transactional
    public void updateAuthRole(User user) {
        UserDto userDto = UserDto.builder()
                .code(user.getCode())
                .id(user.getId())
                .authRole("ADMIN")
                .password(user.getPassword())
                .username(user.getUsername())
                .userTel(user.getUserTel())
                .user_joinDate(user.getUser_joinDate())
                .managerScore(user.getManagerScore())
                .dropoutReqCheck(user.getDropoutReqCheck())
                .build();

        updateUser(userDto);
    }

    @Transactional
    public String updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + userDto.getId()));
        user.update(userDto.getAuthRole(), userDto.getUserTel(), userDto.getDropoutReqCheck());

        return userRepository.save(userDto.toEntity()).getId();
    }
}
