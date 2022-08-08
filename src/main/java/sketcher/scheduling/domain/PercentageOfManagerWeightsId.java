package sketcher.scheduling.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PercentageOfManagerWeightsId implements Serializable {

    @JoinColumn(name = "high")
    Integer high;

    @JoinColumn(name = "middle")
    Integer middle;

    @JoinColumn(name = "low")
    Integer low;
}
