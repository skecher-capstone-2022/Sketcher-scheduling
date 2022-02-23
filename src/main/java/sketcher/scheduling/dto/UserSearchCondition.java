package sketcher.scheduling.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCondition {
    private String list_align;
    private String type;
    private String keyword;

    @Builder
    public UserSearchCondition(String list_align, String type, String keyword) {
        this.list_align = list_align;
        this.type = type;
        this.keyword = keyword;
    }
}