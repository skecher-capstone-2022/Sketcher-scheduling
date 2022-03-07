package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepositoryCustomImpl;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerHopeTimeController {

    private final ManagerHopeTimeService hopeTimeService;
    private final UserService userService;
//    private final AuthenticationManager authenticationManager;

    @RequestMapping(value = "/check_hopetime", method = RequestMethod.GET)
    public String step2_2(HttpServletRequest request, @RequestParam("userid") String userid, Model model) {
        model.addAttribute("userid", userid);
        return "user/step2-2";
    }

    @RequestMapping("/saveHopeTime")
    public String saveHopeTime(@RequestParam List<String> worktime,
                               @RequestParam("userid") String userid) {
//        System.out.println("[userid]" + userid);
        if (userid != null) {
            User user = userService.loadUserByUsername(userid);
            worktimeSplit(worktime, user);
        }
        return "redirect:/step3";
    }

    @RequestMapping(value = "/updateManager", method = RequestMethod.POST)
    public String updateHopeTime(@RequestParam List<String> worktime,
                                 @RequestParam String userid,
                                 @RequestParam String username,
                                 @RequestParam String userTel) {

        if (userid != null) {
            User user = userService.loadUserByUsername(userid);
            hopeTimeService.updateHopeTimeCheck(user, username, userTel);
            hopeTimeService.deleteByUserId(userid);
            worktimeSplit(worktime, user);

            //세션 등록
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return "redirect:/manager_mypage";
    }

    private void worktimeSplit(List<String> worktime, User user) {
        for (String s : worktime) {
            String[] time = s.split(":");
            int startTime = Integer.parseInt(time[0]);
            int endTime = Integer.parseInt(time[1]);

            hopeTimeService.saveManagerHopeTime(new ManagerHopeTimeDto(startTime, endTime, user));
        }
    }
}
