package sketcher.scheduling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.service.UserService;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserService userService) {
		return args ->
				IntStream.rangeClosed(1, 24).forEach(i -> {
					UserDto user =  UserDto.builder()
							.id("user"+i)
							.authRole("MANAGER")
							.password("1234")
							.username("이혜원"+i)
							.userTel("010-1234-5678")
							.build();

					userService.saveUser(user);
				});
	}

}
