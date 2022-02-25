package sketcher.scheduling.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import sketcher.scheduling.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "사용자")
public class UserDto {

    private Integer code;
    private String id;
    private String authRole;
    private String password;
    private String username;
    private String userTel;
    private LocalDateTime user_joinDate;
    private Double managerScore;
    private Character dropoutReqCheck;

    @Builder
	public UserDto(Integer code, String id, String authRole, String password, String username, String userTel, LocalDateTime user_joinDate, Double managerScore, Character dropoutReqCheck) {
        this.code = code;
        this.id = id;
        this.authRole = authRole;
        this.password = password;
        this.username = username;
        this.userTel = userTel;
        this.user_joinDate = user_joinDate;
        this.managerScore = managerScore;
        this.dropoutReqCheck = dropoutReqCheck;
    }

    public User toEntity() {
        return User.builder()
                .code(code)
                .id(id)
                .authRole(authRole)
                .password(password)
                .username(username)
                .userTel(userTel)
                .user_joinDate(user_joinDate)
                .managerScore(managerScore)
                .dropoutReqCheck(dropoutReqCheck)
                .build();
    }
}
