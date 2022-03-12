package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagerHopeTimeController {

    private final ManagerHopeTimeService hopeTimeService;
    private final UserService userService;

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
            for (String s : worktime) {
                String[] time = s.split(":");
                int startTime = Integer.parseInt(time[0]);
                int endTime = Integer.parseInt(time[1]);

                User user = userService.loadUserByUsername(userid);
                hopeTimeService.saveManagerHopeTime(new ManagerHopeTimeDto(startTime, endTime, user));
            }
        }
        return "redirect:/step3";
    }


}
