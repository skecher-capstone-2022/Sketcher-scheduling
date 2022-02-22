package sketcher.scheduling.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDataDto {
    private String code;
    private String status;
    private String message;
    private Object item;
}
