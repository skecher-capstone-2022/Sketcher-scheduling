package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Integer saveSchedule(ScheduleDto scheduleDto){
        scheduleRepository.save(scheduleDto.toEntity());
        return scheduleDto.getId();
    }

}
