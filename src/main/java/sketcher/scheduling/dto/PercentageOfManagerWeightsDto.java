package sketcher.scheduling.dto;

import lombok.*;
import sketcher.scheduling.domain.PercentageOfManagerWeights;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PercentageOfManagerWeightsDto {

    private Integer id;
    private Integer high;
    private Integer middle;
    private Integer low;

    @Builder
    public PercentageOfManagerWeightsDto(Integer id, Integer high, Integer middle, Integer low) {
        this.id = id;
        this.high = high;
        this.middle = middle;
        this.low = low;
    }

    public PercentageOfManagerWeights toEntity() {
        return PercentageOfManagerWeights.builder()
                .id(id)
                .high(high)
                .middle(middle)
                .low(low)
                .build();
    }
}
