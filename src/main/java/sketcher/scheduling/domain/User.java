package sketcher.scheduling.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.Manager;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
public class User{


    /**
     * NotEmpty 어노테이션은 우선 필요에 따라 설정하시거나 빼시면 될 것 같아요 !
     * NotEmpty 가 들어가면 테스트시에도 무조건 들어가야 하는 값(NULL = X)이에요!
     * Column 은 DB 에 들어가는 이름입니다. id 로 사용 -> DB 에는 user_id 로 저장.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Integer code;

    @Column(name = "user_id")
    private String id;

    @Column(name = "auth_role")
    private String authRole;

    @Column(name = "user_pw")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "비밀번호를 3~12자로 입력해주세요.")
    private String password;

    @Column(name = "user_name")
//    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_joindate")
    private LocalDateTime user_joinDate;

    @Column(name = "manager_score")
    private Double managerScore;

    @Column(name = "dropout_req_check")
    private Character dropoutReqCheck;

    @OneToMany(mappedBy = "user")
    private List<ManagerHopeTime> managerHopeTimeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ManagerWorkingSchedule> managerWorkingScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ManagerAssignSchedule> managerAssignScheduleList = new ArrayList<>();

    protected User() {
    }

    @Builder
    public User(Integer code, String id, String authRole, String password, String username, String userTel, LocalDateTime user_joinDate, Double managerScore, Character dropoutReqCheck) {
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
}
