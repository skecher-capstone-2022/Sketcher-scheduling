package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;

    public Integer saveManagerAssignSchedule(ManagerAssignScheduleDto managerAssignScheduleDto){
        managerAssignScheduleRepository.save(managerAssignScheduleDto.toEntity());
        return managerAssignScheduleDto.getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user){
        return user.getManagerAssignScheduleList();
    }

    public String deleteById(String username){
        return username;
    }
}
