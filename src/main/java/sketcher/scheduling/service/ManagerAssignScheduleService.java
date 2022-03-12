package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.omg.SendingContext.RunTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public List<ManagerAssignSchedule> findAll(){
        return managerAssignScheduleRepository.findAll();
    }

    public Optional<ManagerAssignSchedule> findById(Integer id) {
        return managerAssignScheduleRepository.findById(id);
    }

    @Transactional(rollbackFor = {NoSuchElementException.class})
    public Integer saveManagerAssignSchedule(ManagerAssignScheduleDto managerAssignScheduleDto) throws NoSuchElementException{
        managerAssignScheduleDto.setUpdateReq(null);
        return managerAssignScheduleRepository.save(managerAssignScheduleDto.toEntity()).getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user){
        return user.getManagerAssignScheduleList();
    }

//    public Optional<ManagerAssignSchedule> findBySchedule(Schedule schedule){
//        return managerAssignScheduleRepository.findBySchedule(schedule);
//    }

    @Transactional
    public Integer deleteByUser(User user){
        User user1 = userRepository.findByUsername(user.getUsername()).get();
        managerAssignScheduleRepository.deleteByUser(user1);
        return user1.getCode();
    }

    public Optional<ManagerAssignSchedule> findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(User user, LocalDateTime startDate, LocalDateTime endDate){
        return managerAssignScheduleRepository.findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(user, startDate, endDate);
    }

//    @Transactional
//    public Integer deleteBySchedule(Schedule schedule){
//        Schedule schedule1 = scheduleRepository.findById(schedule.getId()).get();
//        managerAssignScheduleRepository.deleteBySchedule(schedule1);
//        scheduleRepository.deleteById(schedule1.getId());
//        return schedule1.getId();
//    }
    //    @Transactional
//    public void update(Integer id, ScheduleDto dto){
//        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 스케줄이 없습니다. " + id));
//
//            schedule.update(dto.getScheduleDateTimeStart(), dto.getScheduleDateTimeEnd());
//    }
    @Transactional
    public void update(Integer id, ManagerAssignScheduleDto dto){
        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 스케줄이 없습니다." + id));

        managerAssignSchedule.update(dto.getScheduleDateTimeStart(), dto.getScheduleDateTimeEnd());
    }
    @Transactional
    public void deleteById(Integer id){
        managerAssignScheduleRepository.deleteById(id);
    }

}
