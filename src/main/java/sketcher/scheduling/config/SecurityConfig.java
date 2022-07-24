package sketcher.scheduling.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sketcher.scheduling.service.UserService;

@Configuration
@EnableWebSecurity  //Spring Security를 활성화
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 관련 어노테이션 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    //WebSecurity는 FilterChainProxy를 생성하는 필터입니다. 다양한 Filter 설정을 적용할 수 있습니다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/font/**");
        // Spring Security에서 해당 요청은 인증 대상에서 제외시킵니다. = 모두 접근 가능
    }


    //HttpSecurity를 통해 HTTP 요청에 대한 보안을 설정할 수 있습니다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        모든 작업 끝나면 경로마다 권한 부여해야 함
        http.csrf().disable().authorizeRequests()
//                .antMatchers("/calendar").hasAuthority("MANAGER")  //인증 사용자만 허용
//                .antMatchers("/calendar_admin").hasAuthority("ADMIN")   //인증 사용자만 허용
                .antMatchers("/calendar").authenticated()   //인증 사용자만 허용
                .antMatchers("/calendar_admin").authenticated()   //인증 사용자만 허용
                .antMatchers("/login").anonymous()    //인증되지 않은 사용자만 허용
//                .antMatchers("/**").permitAll()    //모든 사용자 허용
                .and()

             .formLogin()
                .usernameParameter("userid")
                .passwordParameter("password")
                .loginPage("/login")                .permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 경로를 지정
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);   // 세션 날리기

//        http.exceptionHandling()
//                .accessDeniedPage("/login");  // 에러 페이지 만들게되면 설정해도 좋을 듯
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //userid와 password 파라미터를 설정할 수 있음 (UsernamePasswordAuthenticationFilter클래스를 상속받아 만든 커스텀 클래스)
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/loginProcess");
//        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
//        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

//    @Bean
//    public CustomLoginSuccessHandler customLoginSuccessHandler() {
//        return new CustomLoginSuccessHandler();
//    }
//
//    @Bean
//    public CustomLoginFailureHandler customLoginFailureHandler() {
//        return new CustomLoginFailureHandler();
//    }

    @Bean//CustomAuthenticationProvider : 인증 처리 핵심 로직
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService, bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }

	@Bean
    @Override // AuthenticationManager 클래스를 오버라이딩해서 Bean으로 등록
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); // 회원수정 후에 세션을 유지
    }
}
