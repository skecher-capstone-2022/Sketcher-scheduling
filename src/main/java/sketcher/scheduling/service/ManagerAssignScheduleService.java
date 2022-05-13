package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ManagerAssignScheduleRepositoryCustomImpl;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerAssignScheduleService {

    private final ManagerAssignScheduleRepository managerAssignScheduleRepository;
    private final ManagerAssignScheduleRepositoryCustomImpl scheduleRepositoryCustom;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @PersistenceContext
    private EntityManager em;

    public List<ManagerAssignSchedule> findAll() {
        return managerAssignScheduleRepository.findAll();
    }

    public List<ManagerAssignSchedule> AssignScheduleFindAll() {
        return em.createQuery("select s from ManagerAssignSchedule s"
                      + " join fetch s.user u"
                        , ManagerAssignSchedule.class)
                .getResultList();
    }

    public Optional<ManagerAssignSchedule> findById(Integer id) {
        return managerAssignScheduleRepository.findById(id);
    }

    @Transactional(rollbackFor = {NoSuchElementException.class})
    public Integer saveManagerAssignSchedule(ManagerAssignScheduleDto managerAssignScheduleDto) throws NoSuchElementException {
        managerAssignScheduleDto.setUpdateReq(null);
        return managerAssignScheduleRepository.save(managerAssignScheduleDto.toEntity()).getId();
    }

    public List<ManagerAssignSchedule> findByUser(User user) {
        return user.getManagerAssignScheduleList();
    }

    public Optional<ManagerAssignSchedule> findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return managerAssignScheduleRepository.findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(user, startDate, endDate);
    }

    @Transactional
    public Integer deleteByUser(User user) {
        User user1 = userRepository.findByUsername(user.getUsername()).get();
        managerAssignScheduleRepository.deleteByUser(user1);
        return user1.getCode();
    }

    @Transactional
    public List<ManagerAssignSchedule> findByUserId(String id) {
        return scheduleRepositoryCustom.findByUserId(id);
    }

    @Transactional
    public void update(Integer id, ManagerAssignScheduleDto dto) {
        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 스케줄이 없습니다." + id));

        managerAssignSchedule.update(dto.getScheduleDateTimeStart(), dto.getScheduleDateTimeEnd());
    }

    @Transactional
    public void deleteById(Integer id) {
        managerAssignScheduleRepository.deleteById(id);
    }

}
