package sketcher.scheduling.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
//import static sketcher.scheduling.domain.QUser.user;

//import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(false)
public class UserServiceTest {

    @Autowired
    EntityManager em;

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

    @Test
    public void 로그인() {
        //given
        UserDto user = UserDto.builder()
                .id("taeong111")
                .authRole("MANAGER")
                .password("12345")
                .username("박태영")
                .userTel("1234-5678")
                .build();

        //when
        userService.saveUser(user);
        String userid = "taeong111";
        String password = "12345";

//        em.flush();
//        em.clear();

        //then
        User loadUser = userService.loadUserByUsername(userid);

        Assertions.assertEquals(password, loadUser.getPassword());


    }

    @Test
    public void 아이디중복검사() {
        //given
        UserDto user1 = UserDto.builder()
                .id("taeong")
                .authRole("MANAGER")
                .password("12345")
                .username("박태영")
                .userTel("1234-5678")
                .build();

        UserDto user2 = UserDto.builder()
                .id("taeong")
                .authRole("MANAGER")
                .password("12345")
                .username("박태영")
                .userTel("1234-5678")
                .build();

        //when
        String saveduser = userService.saveUser(user1);
        boolean result = userService.userIdCheck(user2.getId());
//        이미 있는 아이디이기 때문에 flase가 나와야 함

        //then
        Assertions.assertEquals(result, false);
    }

}