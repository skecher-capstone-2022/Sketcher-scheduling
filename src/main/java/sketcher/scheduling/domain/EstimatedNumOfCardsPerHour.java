package sketcher.scheduling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Estimated_Num_Of_Cards_Per_Hour")
@Getter @Setter
@NoArgsConstructor
public class EstimatedNumOfCardsPerHour {
    @Id
    @JoinColumn(name = "time")
    private Integer time;

    @JoinColumn(name = "num_of_cards")
    private Integer numOfCards;

    public EstimatedNumOfCardsPerHour(Integer time, Integer numOfCards) {
        this.time = time;
        this.numOfCards = numOfCards;
    }
}
