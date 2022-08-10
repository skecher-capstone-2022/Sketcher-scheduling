package sketcher.scheduling.algorithm;

import lombok.Getter;
import lombok.Setter;
import sketcher.scheduling.object.HopeTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Manager implements Comparable<Manager> {
    private Integer code;
    private List<HopeTime> hopeTimeList;
    private Integer hopeTimeCount;
    private Integer totalAssignTime;
    private Integer dayAssignTime;
    private Integer weight;
    private boolean previousAssignFlag;
    private List<Schedule> assignScheduleList;
    private Integer priorityScore;

    public void makePriority() {

    }

    public Manager() {
        dayAssignTime = 0;
        this.hopeTimeList = new ArrayList<>();
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

            System.out.println("totalAssignTime" + totalAssignTime);
            System.out.println("dayAssignTime" + dayAssignTime);
        }
        assignScheduleList.add(newNode);
    }

    @Override
    public int compareTo(Manager manager) {
        return this.totalAssignTime - manager.totalAssignTime;
    }
}
