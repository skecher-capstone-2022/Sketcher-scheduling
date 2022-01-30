package sketcher.scheduling.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.repository.UserRepository;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void 회원_가입() {
        //given
        User user = User.builder()
                .id("min")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();
        //when
        String user1 = userService.saveUser(user);
        //then
        Assertions.assertThat(user1 userRepository.findByUsername(user1));
    }
}