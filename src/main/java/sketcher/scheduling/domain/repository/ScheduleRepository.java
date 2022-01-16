package sketcher.scheduling.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.ManagerWorkingSchedule;
import sketcher.scheduling.domain.Schedule;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

        private final EntityManager em;

        /**
         * 스케줄 저장
         */
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
         * 매니저 한명 배정 스케줄 조회
         */
        public ManagerAssignSchedule findManagerAssignSchedule(){

        }

        /**
         * 전체 매니저 수행 스케줄 조회
         */
        public List<ManagerWorkingSchedule> findAllManagerWorkingSchedule(){

        }
        /**
         * 매니저 한명 수행 스케줄 조회
         */
        public ManagerAssignSchedule findManagerWorkingSchedule() {

        }
}
