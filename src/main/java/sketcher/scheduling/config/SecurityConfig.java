package sketcher.scheduling.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import sketcher.scheduling.domain.User;
import sketcher.scheduling.service.UserService;

@Configuration
@EnableWebSecurity  //Spring Security를 활성화
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 관련 어노테이션 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    //로그인 요청 시, 입력된 유저 정보와 DB의 회원정보를 비교해 인증된 사용자인지 체크하는 로직이 정의되어 있어야 함


    //WebSecurity는 FilterChainProxy를 생성하는 필터입니다. 다양한 Filter 설정을 적용할 수 있습니다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/font/**");
        // Spring Security에서 해당 요청은 인증 대상에서 제외시킵니다. = 모두 접근 가능
    }


    //HttpSecurity를 통해 HTTP 요청에 대한 보안을 설정할 수 있습니다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
//                .antMatchers("/manager/**").hasRole("MANAGER")  //인증 사용자만 허용
//                .antMatchers("/admin/**").hasRole("ADMIN")   //인증 사용자만 허용
//                .antMatchers("/schedule/**").authenticated()   //인증 사용자만 허용
//                .antMatchers("/auth/**").anonymous()    //인증되지 않은 사용자만 허용
                .antMatchers("/**").permitAll()    //모든 사용자 허용
                .and()

                .formLogin()
                .loginPage("/loginView")
                .successForwardUrl("/calendar")
                .failureForwardUrl("/loginView")
                .permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 경로를 지정
                .logoutSuccessUrl("/loginView")
                .invalidateHttpSession(true);   // 세션 날리기

//        http.exceptionHandling()
//                .accessDeniedPage("/login");
    }

//    @Override   //BCrypt타입의 password를 다시 인코딩해주는 역할
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService)
//                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
//                // loadUserByUsername() 구현해야함 (서비스 참고)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //userid와 password 파라미터를 설정할 수 있음 (UsernamePasswordAuthenticationFilter클래스를 상속받아 만든 커스텀 클래스)
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean//CustomAuthenticationProvider : 인증 처리 핵심 로직
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService, bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }


}
