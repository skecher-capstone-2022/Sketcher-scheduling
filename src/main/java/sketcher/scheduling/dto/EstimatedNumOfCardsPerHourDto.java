package sketcher.scheduling.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimatedNumOfCardsPerHourDto {

    private Integer time;


    private Integer numOfCards;

    @Builder
    public EstimatedNumOfCardsPerHourDto(Integer time, Integer numOfCards) {
        this.time = time;
        this.numOfCards = numOfCards;
    }
}
