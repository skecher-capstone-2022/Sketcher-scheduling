package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.repository.ScheduleUpdateReqRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleUpdateReqService {
    private final ScheduleUpdateReqRepository updateReqRepository;

    @Transactional
    public Integer saveScheduleUpdateReq(ScheduleUpdateReqDto dto){
        return updateReqRepository.save(dto.toEntity()).getId();
    }

    public List<ScheduleUpdateReq> findAll(){
        return updateReqRepository.findAll();
    }
}
