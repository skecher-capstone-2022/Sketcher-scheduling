package sketcher.scheduling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingApplication.class, args);
	}
//
//	@Bean
//	public CommandLineRunner initData(UserService userService, ManagerHopeTimeService hopeService) {
//		return args ->
//				IntStream.rangeClosed(4, 22).forEach(i -> {
//					UserDto user1 =  UserDto.builder()
//							.id("user"+i)
//							.authRole("MANAGER")
//							.password(new BCryptPasswordEncoder().encode("1234"))
//							.username("이혜원"+i)
//							.userTel("010-1234-5678")
//							.user_joinDate(LocalDateTime.now())
//							.managerScore(i*2/5.0)
//							.build();
//
//					userService.saveUser(user1);
//
////					UserDto user2 =  UserDto.builder()
////							.id(i+"user")
////							.authRole("MANAGER")
////							.password("1234")
////							.username("이혜원"+i)
////							.userTel("010-1234-5678")
////							.user_joinDate(LocalDateTime.now())
////							.managerScore(i*2/5.0+0.2)
////							.build();
////
////					userService.saveUser(user2);
//
////					ManagerHopeTimeDto hope1 = ManagerHopeTimeDto.builder()
////							.user(user1.toEntity())
////							.start_time(12)
////							.finish_time(18)
////							.build();
////
////					hopeService.saveManagerHopeTime(hope1);
////
////					ManagerHopeTimeDto hope2 = ManagerHopeTimeDto.builder()
////							.user(user1.toEntity())
////							.start_time(18)
////							.finish_time(24)
////							.build();
////
////					hopeService.saveManagerHopeTime(hope2);
////
////
////					ManagerHopeTimeDto hope3 = ManagerHopeTimeDto.builder()
////							.user(user1.toEntity())
////							.start_time(6)
////							.finish_time(12)
////							.build();
////
////					hopeService.saveManagerHopeTime(hope3);
//
////					ManagerHopeTimeDto hope4 = ManagerHopeTimeDto.builder()
////							.user(user1.toEntity())
////							.start_time(0)
////							.finish_time(6)
////							.build();
////
////					hopeService.saveManagerHopeTime(hope4);
//
//
////					UserDto user3 =  UserDto.builder()
////							.id("admin"+i)
////							.authRole("ADMIN")
////							.password("1234")
////							.username("박태영"+i)
////							.userTel("010-1234-5678")
////							.build();
////					userService.saveUser(user3);
//				});
//
//
//	}

}
