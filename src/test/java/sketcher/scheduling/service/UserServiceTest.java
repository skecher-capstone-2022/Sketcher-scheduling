package sketcher.scheduling.service;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원_가입() {
        //given
        UserDto user = UserDto.builder()
                .id("min")
                .authRole("user")
                .password("1234")
                .username("정민환")
                .userTel("1234-5678")
                .build();

        //when
        String user1 = userService.saveUser(user);
        String user3 = "min2";
        //then
        User user2 = userRepository.findById(user.getId()).get();
        String userby = user2.getId();

        Assertions.assertEquals(user1, userby);


    }
}