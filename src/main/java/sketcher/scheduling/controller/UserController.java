package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        return "user/login";
    }

    @RequestMapping(value = "/step1", method = RequestMethod.GET)
    public String step1(HttpServletRequest request) {
        return "user/step1";
    }

    @RequestMapping(value = "/step2", method = RequestMethod.GET)
    public String step2(HttpServletRequest request) {
        return "user/step2";
    }

    @RequestMapping(value = "/step3", method = RequestMethod.GET)
    public String step3(HttpServletRequest request) {
        return "user/step3";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(UserDto joinUser){
        //희망시간도 추가해야함
        userService.saveUser(joinUser);

        return "redirect:/step3";
    }
}
