package sketcher.scheduling.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.ManagerWorkingSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.repository.ScheduleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 스케줄 저장
     */
    @Transactional
    public Long saveSchedule(Schedule schedule){

    }

    /**
     * 스케줄 조회
     */
    public Schedule findOneSchedule(Long ScheduleId){

    }

    /**
     * 전체 스케줄 조회
     */
    public Schedule findAllSchedule(){

    }

    /**
     * 매니저 희망 스케줄 저장
     */
    @Transactional
    public void saveManagerHopeTime(){

    }

    /**
     * 매니저 희망 스케줄 조회
     */
    public List<ManagerHopeTime> findAllManagerHopeTimes(){

    }
    /**
     * 전체 매니저 배정 스케줄 조회
     */
    public List<ManagerAssignSchedule> findAllManagerAssignSchedule(){

    }
    /**
     * 매니저 개인 배정 스케줄 조회
     */
    public ManagerAssignSchedule findManagerAssignSchedule(){

    }

    /**
     * 전체 매니저 수행 스케줄 조회
     */
    public List<ManagerWorkingSchedule> findAllManagerWorkingSchedule(){

    }
    /**
     * 매니저 개인 수행 스케줄 조회
     */
    public ManagerAssignSchedule findManagerWorkingSchedule(){

    }

    /**
     * 매니저 개인 스케줄 삭제 요청
     */
    @Transactional
    public ManagerAssignSchedule deleteScheduleRequest(){

    }

    /**
     * 매니저 개인 스케줄 삭제
     */
    @Transactional
    public ManagerAssignSchedule deleteSchedule(){

    }

    /**
     * 스케줄 수정
     */
    @Transactional
    public Integer updateSchedule(Schedule schedule){

    }

    /**
     * 매니저 수정 요청 스케줄 조회
     */
    public Integer findUpdateRequestSchedule(){

    }
}
}
