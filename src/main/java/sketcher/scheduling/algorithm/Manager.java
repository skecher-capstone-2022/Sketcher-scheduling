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

    public void updateAssignScheduleList(Schedule currentNode,Schedule newNode) {
        if (currentNode != null) {
            assignScheduleList.remove(currentNode);
        }
        assignScheduleList.add(newNode);
    }
}
