package sketcher.scheduling.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.object.HopeTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Manager implements Comparable<Manager> {
    private Integer code;
    private List<HopeTime> hopeTimeList = new ArrayList<>();
    private Integer hopeTimeCount;
    private Integer totalAssignTime;
    private Integer dayAssignTime;
    private Integer weight;
    private boolean previousAssignFlag;
    private List<Schedule> assignScheduleList;
    private Integer priorityScore;

    public void makePriority() {

    }


    public Manager(Integer code, List<HopeTime> hopeTimeList, Integer hopeTimeCount, Integer totalAssignTime, Integer dayAssignTime, Integer weight) {
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
        } else {
            totalAssignTime++;
            dayAssignTime++;
        }
        assignScheduleList.add(newNode);
    }

    public boolean isContrainHopeTimes(int time) {
        for (HopeTime hopeTime : hopeTimeList) {
            if (matchingHopeTimeType(time, hopeTime, HopeTime.DAWN)) return true;
            if (matchingHopeTimeType(time, hopeTime, HopeTime.MORNING)) return true;
            if (matchingHopeTimeType(time, hopeTime, HopeTime.AFTERNOON)) return true;
            if (matchingHopeTimeType(time, hopeTime, HopeTime.EVENING)) return true;
        }
        return false;
    }

    private boolean matchingHopeTimeType(int time, HopeTime hopeTime, HopeTime dawn) {
        if (time >= hopeTime.getStart_time() && time < hopeTime.getFinish_time() && hopeTime == dawn) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Manager manager) {
        return this.totalAssignTime - manager.totalAssignTime;
    }
}
