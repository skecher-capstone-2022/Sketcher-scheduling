package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.repository.ScheduleUpdateReqRepository;
import sketcher.scheduling.repository.ScheduleUpdateReqRepositoryCustom;
import sketcher.scheduling.repository.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleUpdateReqService {
    private final ScheduleUpdateReqRepository updateReqRepository;
    private final ScheduleUpdateReqRepositoryCustom updateReqRepoCustom;

    @Transactional
    public Integer saveScheduleUpdateReq(ScheduleUpdateReqDto dto) {
        dto.setReqAcceptCheck('N');
        dto.setReqTime(LocalDateTime.now());
        return updateReqRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public void updateCheck(Integer id) {
        ScheduleUpdateReq req = updateReqRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not Found Id"));

//        //기존 start-endtime의 차이 계산
//        req.getAssignSchedule().

        ScheduleUpdateReq assignReq = ScheduleUpdateReq.builder()
                .id(req.getId())
                .changeDate(req.getChangeDate())
                .assignSchedule(req.getAssignSchedule())
                .reqAcceptCheck('Y')
                .build();
        updateReqRepository.save(assignReq);
    }

    public Optional<ScheduleUpdateReq> findById(int id) {
        return updateReqRepository.findById(id);
    }

    public List<ScheduleUpdateReq> findAll() {
        return updateReqRepository.findAll();
    }

    public List<ScheduleUpdateReq> updateReqResultList(String sort) {
        return updateReqRepoCustom.sort(sort);
    }
}
