package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.repository.ManagerAssignScheduleRepository;
import sketcher.scheduling.repository.ScheduleUpdateReqRepository;
import sketcher.scheduling.repository.ScheduleUpdateReqRepositoryCustom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleUpdateReqService {
    private final ManagerAssignScheduleRepository assignScheduleRepository;
    private final ManagerAssignScheduleService assignScheduleService;
    private final ScheduleUpdateReqRepository updateReqRepository;
    private final ScheduleUpdateReqRepositoryCustom updateReqRepoCustom;

    @Transactional
    public Integer saveScheduleUpdateReq(ManagerAssignSchedule assignSchedule, LocalDateTime modifiedStartDate,LocalDateTime modifiedEndDate ) {
        Integer saveReqId = saveUpdateReq(assignSchedule, modifiedStartDate, modifiedEndDate);
        ScheduleUpdateReq savedUpdateReq = updateReqRepository.findById(saveReqId).get();
        assignSchedule.addUpdateReq(savedUpdateReq);
        return saveReqId;
    }

    public Integer saveUpdateReq(ManagerAssignSchedule assignSchedule,LocalDateTime modifiedStartDate, LocalDateTime modifiedEndDate) {
        ScheduleUpdateReqDto scheduleUpdateReqDto = ScheduleUpdateReqDto.builder()
                .assignSchedule(assignSchedule)
                .reqAcceptCheck('N')
                .changeStartDate(modifiedStartDate)
                .changeEndDate(modifiedEndDate)
                .reqTime(LocalDateTime.now())
                .build();
        return updateReqRepository.save(scheduleUpdateReqDto.toEntity()).getId();
    }

    @Transactional
    public void duplicateUpdateRequest(ManagerAssignSchedule assignSchedule, LocalDateTime modifiedStartDate,LocalDateTime modifiedEndDate ){
        ScheduleUpdateReq scheduleUpdateReq = updateReqRepository.findById(assignSchedule.getUpdateReq().getId()).get();
        scheduleUpdateReq.update(assignSchedule,modifiedStartDate,modifiedEndDate);
    }

    @Transactional
    public void acceptReq(Integer id) {
        ScheduleUpdateReq updateReq = updateReqRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not Found Id"));
        ManagerAssignSchedule assignSchedule = updateReq.getAssignSchedule();
        assignSchedule.update(updateReq.getChangeStartDate(),updateReq.getChangeEndDate());
        updateReq.updateReqAcceptCheckToY();
    }

    public Optional<ScheduleUpdateReq> findById(int id) {
        return updateReqRepository.findById(id);
    }

    public List<ScheduleUpdateReq> findAll() {
        return updateReqRepository.findAll();
    }


    @Transactional
    public long countByWeekNotAcceptUpdateReq() {
        return updateReqRepoCustom.countByWeekNotAcceptUpdateReq();
    }

    @Transactional
    public long countByWeekUpdateReq() {
        return updateReqRepoCustom.countByWeekUpdateReq();
    }

}
