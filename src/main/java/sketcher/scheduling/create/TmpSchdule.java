package sketcher.scheduling.create;

import lombok.Getter;
import lombok.Setter;

//스케줄 노드
@Getter
@Setter
public class TmpSchdule {
    public final int MAX_TIME_LENGTH = 2;
    private Integer id;
    private int[] time = new int[MAX_TIME_LENGTH];
    private int weight;

    public TmpSchdule(Integer id, int[] time, int weight) {
        this.id = id;
        this.time = time;
        this.weight = weight;
    }
}
