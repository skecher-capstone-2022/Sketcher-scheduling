package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ManagerAssignScheduleRepositoryCustomImpl;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;
    private final ManagerAssignScheduleRepositoryCustomImpl scheduleRepositoryCustom;
    private final UserRepository userRepository;

    public Optional<ManagerAssignSchedule> findById(Integer id) {
        return managerAssignScheduleRepository.findById(id);
    }

    @Transactional
    public Integer saveManagerAssignSchedule(ManagerAssignScheduleDto managerAssignScheduleDto){
        managerAssignScheduleDto.setUpdateReq(null);
        return managerAssignScheduleRepository.save(managerAssignScheduleDto.toEntity()).getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user){
        return user.getManagerAssignScheduleList();
    }

    @Transactional
    public Integer deleteByUser(User user){
        User user1 = userRepository.findByUsername(user.getUsername()).get();
        managerAssignScheduleRepository.deleteByUser(user1);
        return user1.getCode();
    }

    @Transactional
    public List<ManagerAssignSchedule> findByUserId(String id) {
        return scheduleRepositoryCustom.findByUserId(id);
    }

}
