package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Integer saveManagerAssignSchedule(ManagerAssignScheduleDto managerAssignScheduleDto){
        managerAssignScheduleRepository.save(managerAssignScheduleDto.toEntity());
        return managerAssignScheduleDto.getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user){
        return user.getManagerAssignScheduleList();
    }

    public Optional<ManagerAssignSchedule> findBySchedule(Schedule schedule){
        return managerAssignScheduleRepository.findBySchedule(schedule);
    }

    @Transactional
    public Integer deleteByUser(User user){
        User user1 = userRepository.findByUsername(user.getUsername()).get();
        managerAssignScheduleRepository.deleteByUser(user1);
        return user1.getCode();
    }

    @Transactional
    public Integer deleteBySchedule(Schedule schedule){
        Schedule schedule1 = scheduleRepository.findById(schedule.getId()).get();
        managerAssignScheduleRepository.deleteBySchedule(schedule1);
        scheduleRepository.deleteById(schedule1.getId());
        return schedule1.getId();
    }
    @Transactional
    public void deleteById(Integer id){
        managerAssignScheduleRepository.deleteById(id);
    }

}
