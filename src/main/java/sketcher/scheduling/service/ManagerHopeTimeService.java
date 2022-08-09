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

import java.util.HashMap;
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

    public HashMap<String, Long> CountByHopeTime() {
        return managerHopeTimeRepositoryCustom.CountByHopeTime();
    }


    @Transactional(readOnly = false)
    public Integer saveManagerHopeTime(ManagerHopeTimeDto managerHopeTimeDto){
        return managerHopeTimeRepository.save(managerHopeTimeDto.toEntity()).getId();
    }

    @Transactional
    public List<ManagerHopeTime> getHopeTimeByUserCode(Integer userCode){
        return managerHopeTimeRepository.getHopeTimeByUserCode(userCode);
    }

    @Transactional(readOnly = false)
    public void deleteByUserId(String id) {
        managerHopeTimeRepositoryCustom.deleteByUserId(id);
    }
}
