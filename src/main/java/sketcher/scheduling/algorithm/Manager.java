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
    private Schedule schedule;

    public void makePriority() {

    }
}
