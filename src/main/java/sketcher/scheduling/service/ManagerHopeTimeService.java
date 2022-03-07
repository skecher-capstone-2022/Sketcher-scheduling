package sketcher.scheduling.service;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ManagerHopeTimeRepository;
import sketcher.scheduling.repository.ManagerHopeTimeRepositoryCustomImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerHopeTimeService {
    private final UserService userService;
    private final ManagerHopeTimeRepository managerHopeTimeRepository;
    private final ManagerHopeTimeRepositoryCustomImpl managerHopeTimeRepositoryCustom;

    public List<ManagerHopeTime> findAll(){
        return managerHopeTimeRepository.findAll();
    }

    public List<ManagerHopeTime> findManagerHopeTimeByUser(User user){
        return user.getManagerHopeTimeList();
    }


    @Transactional(readOnly = false)
    public Integer saveManagerHopeTime(ManagerHopeTimeDto managerHopeTimeDto){
        return managerHopeTimeRepository.save(managerHopeTimeDto.toEntity()).getId();
    }

    public void deleteByUserId(String id) {
        managerHopeTimeRepositoryCustom.deleteByUserId(id);
    }

    public void updateHopeTimeCheck(User user, String username, String userTel) {
        UserDto userDto = UserDto.builder()
                .code(user.getCode())
                .id(user.getId())
                .authRole(user.getAuthRole())
                .password(user.getPassword())
                .username(username)
                .userTel(userTel)
                .user_joinDate(user.getUser_joinDate())
                .managerScore(user.getManagerScore())
                .dropoutReqCheck(user.getDropoutReqCheck())
                .build();

        userService.updateUser(userDto);
        // 여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기때문에 세션값 갱신이 필요함
    }
}
