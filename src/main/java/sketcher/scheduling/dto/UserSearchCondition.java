package sketcher.scheduling.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCondition {
    private String align;
    private String type;
    private String keyword;

    @Builder
    public UserSearchCondition(String align, String type, String keyword) {
        this.align = align;
        this.type = type;
        this.keyword = keyword;
    }
}