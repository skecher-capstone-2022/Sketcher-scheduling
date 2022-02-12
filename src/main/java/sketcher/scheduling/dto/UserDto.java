package sketcher.scheduling.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import sketcher.scheduling.domain.User;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "사용자")
public class UserDto {

    private String id;
    private String authRole;
    private String password;
    private String username;
    private String userTel;
    private Double managerScore;
    private Character dropoutReqCheck;

    @Builder
    public UserDto(String id, String authRole, String password, String username, String userTel, Double managerScore, Character dropoutReqCheck) {
        this.id = id;
        this.authRole = authRole;
        this.password = password;
        this.username = username;
        this.userTel = userTel;
        this.managerScore = managerScore;
        this.dropoutReqCheck = dropoutReqCheck;
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .authRole(authRole)
                .password(password)
                .username(username)
                .userTel(userTel)
                .managerScore(managerScore)
                .dropoutReqCheck(dropoutReqCheck)
                .build();
    }
}
