package sketcher.scheduling.service.createSchedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreateTmpManagerService {
    final int HOPETIME_SIZE = 4;

    List<TmpManager> tmpManagerList = new ArrayList<>();

    private final UserService userService;
    private final ManagerHopeTimeService hopeTimeService;

    public List<TmpManager> getTmpManagerList() {
        return tmpManagerList;
    }


    public void createTmpObject() {
        List<User> realObject = userService.findAllManager();
        for (User user : realObject) {
            int[] hopeTime = setManagerHopeTime(user);
            tmpManagerList.add(createNewTmpManager(user, hopeTime));
        }
    }

    private TmpManager createNewTmpManager(User user, int[] hopeTime) {
        return TmpManager.builder()
                .usercode(user.getCode())
                .userid(user.getId())
                .username(user.getUsername())
                .hopeTime(hopeTime)
                .build();
    }

    private int[] setManagerHopeTime(User user) {
        List<ManagerHopeTime> managerHopeTimeByUser = hopeTimeService.findManagerHopeTimeByUser(user);

        int[] hopeTime = new int[HOPETIME_SIZE];
        for (ManagerHopeTime managerHopeTime : managerHopeTimeByUser) {
            setArrayHopeTime(hopeTime, managerHopeTime);
        }
        return hopeTime;
    }

    private void setArrayHopeTime(int[] hopeTime, ManagerHopeTime managerHopeTime) {
        if (managerHopeTime.getStart_time() == 0)
            hopeTime[0] = 1;
        else if (managerHopeTime.getStart_time() == 6)
            hopeTime[1] = 1;
        else if (managerHopeTime.getStart_time() == 12)
            hopeTime[2] = 1;
        else if (managerHopeTime.getStart_time() == 18)
            hopeTime[3] = 1;
    }


}
