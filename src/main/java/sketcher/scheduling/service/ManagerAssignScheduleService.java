package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.*;

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
    private final ScheduleUpdateReqRepository updateReqRepository;
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

    public Optional<ManagerAssignSchedule> getBeforeSchedule(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return managerAssignScheduleRepository.getBeforeSchedule(user, startDate, endDate);
    }

    @Transactional
    public Integer deleteByUser(User user) throws Exception{
        User user1 = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new Exception("로그인 한 정보가 없습니다."));
        managerAssignScheduleRepository.deleteByUser(user1);
        return user1.getCode();
    }

    @Transactional
    public List<ManagerAssignSchedule> findByUserId(String id) {
        return scheduleRepositoryCustom.findByUserId(id);
    }

    @Transactional
    public long monthAssignWorkByUserId(String id) {
        return scheduleRepositoryCustom.monthAssignWorkByUserId(id);
    }

    @Transactional
    public long weekWorkByUserId(String id) {
        return scheduleRepositoryCustom.weekWorkByUserId(id);
    }

    @Transactional
    public long weekRemainByUserId(String id) {
        return scheduleRepositoryCustom.weekRemainByUserId(id);
    }

    @Transactional
    public long countByTodayAssignManager() {
        return scheduleRepositoryCustom.countByTodayAssignManager();
    }

    @Transactional
    public long weekAssignByUserId(String id) {
        return scheduleRepositoryCustom.weekAssignByUserId(id);
    }

    @Transactional
    public void update(Integer id, ManagerAssignScheduleDto dto) {
        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 스케줄이 없습니다." + id));

        managerAssignSchedule.update(dto.getScheduleDateTimeStart(), dto.getScheduleDateTimeEnd());
    }

    @Transactional
    public List<ManagerAssignSchedule> findAcceptReqCheckIsN() {
        return em.createQuery("select a from ManagerAssignSchedule a inner join ScheduleUpdateReq r " +
                        "on a.updateReq.id = r.id where r.reqAcceptCheck= 'N'"
                , ManagerAssignSchedule.class)
                .getResultList();

    }

    @Transactional
    public void deleteById(Integer id) {
        managerAssignScheduleRepository.deleteById(id);
    }

}
