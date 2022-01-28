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
public class User {

    @Id
    @Column(name = "user_id")
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "아이디를 3~12자로 입력해주세요. [특수문자 X]")
    private String id;

    @NotEmpty
    private String auth_role;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "비밀번호를 3~12자로 입력해주세요.")
    private String password;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String user_name;

    @NotEmpty
    private String user_tel;

    @NotEmpty
    private LocalDateTime user_joinDate;

    private Double manager_score;

    @NotEmpty
    private Character dropout_req_check;

    @OneToMany(mappedBy = "user")
    private List<ManagerHopeTime> managerHopeTimes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ManagerWorkingSchedule> managerWorkingSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ManagerAssignSchedule> managerAssignSchedules = new ArrayList<>();

    protected User() {
    }

    @Builder
    public User(String id, String auth_role, String password, String user_name, String user_tel) {
        this.id = id;
        this.auth_role = auth_role;
        this.password = password;
        this.user_name = user_name;
        this.user_tel = user_tel;
    }
}
