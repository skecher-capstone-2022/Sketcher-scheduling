package sketcher.scheduling.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "Invalid userid or Password";

        // exception 관련 메세지 처리
        if (exception instanceof DisabledException) {
            msg = "DisabledException account";
        } else if(exception instanceof CredentialsExpiredException) {
            msg = "CredentialsExpiredException account";
        } else if(exception instanceof BadCredentialsException) {
            msg = "BadCredentialsException account";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + msg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
