package sketcher.scheduling.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AuthenticationProvider를 통해 인증이 성공될 경우 처리
@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        //세션을 이용하는 방식 (토큰 사용x)
        //나중에 사용자의 정보를 꺼낼 경우에도 SecurityContextHolder의 context에서 조회 가능함
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/calendar");
    }
}
