package sketcher.scheduling.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TempUser {
    public int usercode;
    public int currentTime;
    public int[] hopeTime;
}
