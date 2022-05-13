package sketcher.scheduling.service.createSchedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.exception.NoExsistTmpScheduleException;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreateTmpScheduleService {
    List<TmpSchedule> tmpScheduleList = new ArrayList<>();

    private CreateTmpManagerService tmpManagerService;


    public TmpSchedule updateTmpObject(String cellId, LocalDate date, int time, TmpManager manager) {
        TmpSchedule tmpSchedule;
        if (!isTmpScheduleExsist(cellId)) {
            tmpSchedule = createTmpSchedule(cellId, date, time,manager);
            tmpScheduleList.add(tmpSchedule);
        } else {
            tmpSchedule = (TmpSchedule) findTmpScheduleByCellId(cellId);
            tmpSchedule.updateTmpManager(manager);
        }
        return tmpSchedule;
    }



    //셀 아이디가 존재하는지 확인하는 함수
    public boolean isTmpScheduleExsist(String findCellId) {
        for (TmpSchedule tmpSchedule : tmpScheduleList) {
            if (tmpSchedule.getCellId().equals(findCellId))
                return true;
        }
        return false;
    }

    public Object findTmpScheduleByCellId(String findCellId) {
        for (TmpSchedule tmpSchedule : tmpScheduleList) {
            if (tmpSchedule.getCellId().equals(findCellId))
                return tmpSchedule;
        }
        return new NoExsistTmpScheduleException(findCellId);
    }


    private TmpSchedule createTmpSchedule(String cellId, LocalDate date, int time, TmpManager tmpManager) {
        return TmpSchedule.builder()
                .cellId(cellId)
                .date(date)
                .time(time)
                .tmpManager(tmpManager)
                .build();
    }

    public List<TmpSchedule> getTmpScheduleList(){
        return tmpScheduleList;
    }


    //매니저를 인풋으로 주면 해당 매니저에 배정된 스케줄 셀 리스트를 출력
    public List<TmpSchedule> findTmpSchedulesByTmpManager(TmpManager findManager) {
        List<TmpSchedule> findManagertmpScheduleList = new ArrayList<>();
        for (TmpSchedule tmpSchedule : tmpScheduleList) {
            if (tmpSchedule.getTmpManager() == findManager)
                findManagertmpScheduleList.add(tmpSchedule);
        }
        return findManagertmpScheduleList;
    }
}
