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
import java.time.temporal.ChronoUnit;
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
    public void saveScheduleUpdateReq(ManagerAssignSchedule assignSchedule, LocalDateTime modifiedStartDate,LocalDateTime modifiedEndDate ) {
        Integer saveReqId = saveUpdateReq(assignSchedule, modifiedStartDate, modifiedEndDate);
        ScheduleUpdateReq savedUpdateReq = updateReqRepository.findById(saveReqId).get();
        assignSchedule.updateReqId(savedUpdateReq);
    }

    public Integer saveUpdateReq(ManagerAssignSchedule assignSchedule,LocalDateTime modifiedStartDate, LocalDateTime modifiedEndDate) {
        ScheduleUpdateReqDto scheduleUpdateReqDto = ScheduleUpdateReqDto.builder()
                .assignSchedule(assignSchedule)
                .reqAcceptCheck('N')
                .changeStartDate(modifiedStartDate)
                .changeEndDate(modifiedEndDate)
                .reqTime(LocalDateTime.now())
                .build();
        Integer saveReqId = updateReqRepository.save(scheduleUpdateReqDto.toEntity()).getId();
        return saveReqId;
    }

    // ScheduleUpdateReq updateReq, LocalDateTime scheduleDateTimeStart, LocalDateTime scheduleDateTimeEnd

    @Transactional
    public void updateCheck(Integer id) {

        //TODO 수정 요청 수락
        ScheduleUpdateReq req = updateReqRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not Found Id"));
        ManagerAssignSchedule assignSchedule = req.getAssignSchedule();

//        ManagerAssignSchedule assign_updateReq = ManagerAssignSchedule.builder()
//                .id(assignSchedule.getId())
//                .user(assignSchedule.getUser())
//                .updateReq(req)
//                .scheduleDateTimeStart(req.getChangeDate())
//                .scheduleDateTimeEnd(req.getChangeDate().plusHours(between_time))
//                .build();
//        assignScheduleRepository.save(assign_updateReq);
//
////        req.getAssignSchedule().
//
//        ScheduleUpdateReq assignReq = ScheduleUpdateReq.builder()
//                .id(req.getId())
//                .changeDate(req.getChangeDate())
//                .assignSchedule(req.getAssignSchedule())
//                .reqTime(req.getReqTime())
//                .reqAcceptCheck('Y')
//                .build();
//        updateReqRepository.save(assignReq);
    }

    public Optional<ScheduleUpdateReq> findById(int id) {
        return updateReqRepository.findById(id);
    }

    public List<ScheduleUpdateReq> findAll() {
        return updateReqRepository.findAll();
    }


    public Optional<ScheduleUpdateReq> findByAssignSchedule(ManagerAssignSchedule managerAssignSchedule){
        return updateReqRepository.findByAssignSchedule(managerAssignSchedule);
    }

    //TODO DUPLICATION
    @Transactional
    public void duplicateUpdateRequest(Integer id, ScheduleUpdateReqDto dto){
//        ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 스케줄이 없습니다." + id));
        ScheduleUpdateReq scheduleUpdateReq = updateReqRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 스케줄이 없습니다." + id));
//        scheduleUpdateReq.update(dto.getAssignSchedule(), dto.getChangeDate());
    }
}
