package sketcher.scheduling.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sketcher.scheduling.domain.ManagerHopeTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Manager {
    private Integer code;
    private List<ManagerHopeTime> hopeTimeList = new ArrayList<>();
    private Integer hopeTimeCount;
    private Integer totalAssignTime;
    private Integer dayAssignTime;
    private Integer weight;
    private boolean previousAssignFlag;
    private List<Schedule> assignScheduleList;
    private Integer priorityScore;

    public void makePriority() {

    }


    public Manager(Integer code, List<ManagerHopeTime> hopeTimeList, Integer hopeTimeCount, Integer totalAssignTime, Integer dayAssignTime, Integer weight) {
        this.code = code;
        this.hopeTimeList = hopeTimeList;
        this.hopeTimeCount = hopeTimeCount;
        this.totalAssignTime = totalAssignTime;
        this.dayAssignTime = dayAssignTime;
        this.weight = weight;
        this.assignScheduleList = new ArrayList<>();
    }

    public Schedule findScheduleByTime(int time) {
        for (Schedule schedule : assignScheduleList) {
            if (schedule.getTime() == time) {
                return schedule;
            }
        }
        return null;
    }

    public void updateAssignScheduleList(Schedule currentNode, Schedule newNode) {
        if (currentNode != null) {
            assignScheduleList.remove(currentNode);
        }
        assignScheduleList.add(newNode);
    }

    public boolean isContrainHopeTimes(int time) {
        for (ManagerHopeTime hopeTime : hopeTimeList) {
            if (time >= 0 && time < 6 && hopeTime.getStart_time() == 0) {
                return true;
            }
            if (time >= 6 && time < 12 && hopeTime.getStart_time() == 6) {
                return true;
            }
            if (time >= 12 && time < 18 && hopeTime.getStart_time() == 12) {
                return true;
            }
            if (time >= 18 && time < 24 && hopeTime.getStart_time() == 18) {
                return true;
            }
        }
        return false;
    }

}
