package sketcher.scheduling.repository;

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
        public Integer saveSchedule(Schedule schedule){

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
        public ManagerAssignSchedule findManagerWorkingSchedule() {

        }

        /**
         * 매니저 개인 스케줄 삭제 요청
         */
        public ManagerAssignSchedule deleteScheduleRequest(){

        }

        /**
         * 매니저 개인 스케줄 삭제 요청 조회
         */
        public Integer findDeleteScheduleRequestList(){

        }
        
        /**
         * 매니저 개인 스케줄 삭제
         */
        public ManagerAssignSchedule deleteSchedule(){
                
        }

        /**
         * 스케줄 수정 요청
         */
        public ManagerAssignSchedule updateScheduleRequest(Schedule schedule){

        }

        /**
         * 매니저 수정 요청 스케줄 조회
         */
        public Integer findUpdateRequestSchedule(){

        }

        /**
         *  매니저 스케줄 수정
         */
}
