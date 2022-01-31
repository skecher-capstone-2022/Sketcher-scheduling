package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerWorkingSchedule;
import sketcher.scheduling.dto.ManagerWorkingScheduleDto;
import sketcher.scheduling.repository.ManagerWorkingScheduleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerWorkingScheduleService {

    private final ManagerWorkingScheduleRepository managerWorkingScheduleRepository;

    @Transactional
    public Integer saveManagerWorkingSchedule(ManagerWorkingScheduleDto managerWorkingScheduleDto){
        return managerWorkingScheduleRepository.save(managerWorkingScheduleDto.toEntity()).getId();
    }
}
