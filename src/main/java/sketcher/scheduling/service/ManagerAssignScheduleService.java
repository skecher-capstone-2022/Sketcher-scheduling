package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduelDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;

    public Integer saveManagerAssignSchedule(ManagerAssignScheduelDto managerAssignScheduelDto){
        managerAssignScheduleRepository.save(managerAssignScheduelDto.toEntity());
        return managerAssignScheduelDto.getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user){
        return user.getManagerAssignScheduleList();
    }
}
