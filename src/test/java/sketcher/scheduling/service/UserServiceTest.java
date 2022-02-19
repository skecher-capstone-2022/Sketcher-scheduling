package sketcher.scheduling.service;

import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() {
        //given
        UserDto user = UserDto.builder()
                .id("user1")
                .authRole("MANAGER")
                .password("1234")
                .username("이혜원")
                .userTel("010-1234-5678")
                .build();

        //when
        String user1 = userService.saveUser(user);
        String user3 = "user2";

        //then
        User user2 = userRepository.findById(user.getId()).get();
        String userby = user2.getId();

        Assertions.assertEquals(user1, userby);
    }

    @Test
    public void 페이징() throws Exception {
        //given
        UserDto user1 = UserDto.builder()
                .id("user1")
                .authRole("MANAGER")
                .password("1234")
                .username("이혜원")
                .userTel("010-1234-5678")
                .build();

        UserDto user2 = UserDto.builder()
                .id("user2")
                .authRole("MANAGER")
                .password("1234")
                .username("박태영")
                .userTel("010-1234-5678")
                .build();

        UserDto user3 = UserDto.builder()
                .id("user3")
                .authRole("MANAGER")
                .password("1234")
                .username("김희수")
                .userTel("010-1234-5678")
                .build();

        UserDto user4 = UserDto.builder()
                .id("user4")
                .authRole("MANAGER")
                .password("1234")
                .username("정민환")
                .userTel("010-1234-5678")
                .build();

        UserDto user5 = UserDto.builder()
                .id("user5")
                .authRole("MANAGER")
                .password("1234")
                .username("홍길동")
                .userTel("010-1234-5678")
                .build();

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userService.saveUser(user5);


//        //when
//        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "user_joindate"));
//        Page<User> page = userService.findAllManager(pageRequest);
//
//        //then
//        List<User> content = page.getContent(); //조회된 데이터
//        assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
//        assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
//        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
//        assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
//        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
//        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
    }
}