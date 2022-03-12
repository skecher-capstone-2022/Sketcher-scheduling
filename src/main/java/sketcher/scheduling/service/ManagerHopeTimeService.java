package sketcher.scheduling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.repository.ManagerHopeTimeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerHopeTimeService {

    private final ManagerHopeTimeRepository managerHopeTimeRepository;

    public List<ManagerHopeTime> findAll(){
        return managerHopeTimeRepository.findAll();
    }

    public List<ManagerHopeTime> findManagerHopeTimeByUser(User user){
        return user.getManagerHopeTimeList();
    }

    public Integer saveManagerHopeTime(ManagerHopeTimeDto managerHopeTimeDto){
        return managerHopeTimeRepository.save(managerHopeTimeDto.toEntity()).getId();

    }
}
