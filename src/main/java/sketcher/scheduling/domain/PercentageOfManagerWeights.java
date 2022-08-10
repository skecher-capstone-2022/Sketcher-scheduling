package sketcher.scheduling.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Percentage_Of_Manager_Weights")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PercentageOfManagerWeights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "high")
    private Integer high;

    @JoinColumn(name = "middle")
    private Integer middle;

    @JoinColumn(name = "low")
    private Integer low;

    @Builder
    public PercentageOfManagerWeights(Integer id, Integer high, Integer middle, Integer low) {
        this.id = id;
        this.high = high;
        this.middle = middle;
        this.low = low;
    }

    public void update(Integer high, Integer middle, Integer low) {
        this.high = high;
        this.middle = middle;
        this.low = low;
    }
}
