//package sketcher.scheduling.domain;
//
//import lombok.*;
//
//import javax.persistence.Embeddable;
//import javax.persistence.JoinColumn;
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//@Getter
//@Embeddable
//@EqualsAndHashCode
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PercentageOfManagerWeightsId implements Serializable {
//
//    @JoinColumn(name = "high")
//    Integer high;
//
//    @JoinColumn(name = "middle")
//    Integer middle;
//
//    @JoinColumn(name = "low")
//    Integer low;
//
//    public PercentageOfManagerWeightsId(BigDecimal high, BigDecimal middle, BigDecimal low) {
//        this.high = high.intValue();
//        this.middle = middle.intValue();
//        this.low = low.intValue();
//    }
//
//    public PercentageOfManagerWeightsId(String high, String middle, String low) {
//        this.high = Integer.parseInt(high);
//        this.middle = Integer.parseInt(middle);
//        this.low = Integer.parseInt(low);
//    }
//}
