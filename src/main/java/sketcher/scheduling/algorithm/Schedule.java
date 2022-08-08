package sketcher.scheduling.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Integer id;
    private Integer time;
    private Integer weight;
    private boolean managerWeightFlag;
    private Manager manager;

    public void makeWeight() {

    }
}
