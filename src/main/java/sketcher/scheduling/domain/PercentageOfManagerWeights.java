package sketcher.scheduling.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Percentage_Of_Manager_Weights")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PercentageOfManagerWeights {

    @EmbeddedId
    private PercentageOfManagerWeightsId id;

}
