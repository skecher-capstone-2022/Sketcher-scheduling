package sketcher.scheduling.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.form.LoginForm;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//
//    @NonNull
//    private final BCryptPasswordEncoder passwordEncoder;


    @GetMapping(value = "/login")
    public String loginView(Model model,
                            @RequestParam(value = "error",required = false)String error,
                            @RequestParam(value = "exception",required = false)String exception){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "user/login";
    }

    
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder
                .getContext().getAuthentication());
        return "redirect:/login";
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
    public String signup(UserDto joinUser, RedirectAttributes redirectAttributes){
        userService.saveUser(joinUser);
        redirectAttributes.addAttribute("userid",joinUser.getId());

        return "redirect:/check_hopetime";
    }

    @RequestMapping(value = "/user/idCheck", method = RequestMethod.GET)
    @ResponseBody
    public boolean idCheck(@RequestParam("userid") String user_id) {
        return userService.userIdCheck(user_id);
        //true : 아이디가 존재하지 않을 때
        //false : 아이디가 이미 존재할 때
    }
}
