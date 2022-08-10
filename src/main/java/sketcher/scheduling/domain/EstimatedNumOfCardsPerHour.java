package sketcher.scheduling.domain;

import lombok.*;

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

    @Builder
    public EstimatedNumOfCardsPerHour(Integer time, Integer numOfCards) {
        this.time = time;
        this.numOfCards = numOfCards;
    }


}
