package sketcher.scheduling.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Integer id;
    private Integer time;
    private Integer weight;
    private boolean managerWeightFlag;
    private Manager manager;

    public boolean isManagerWeightFlag() {
        return managerWeightFlag;
    }
    public void makeWeight() {

    }

    public Schedule(Integer id, Integer time, Integer weight, boolean managerWeightFlag) {
        this.id = id;
        this.time = time;
        this.weight = weight;
        this.managerWeightFlag = managerWeightFlag;
    }
}
