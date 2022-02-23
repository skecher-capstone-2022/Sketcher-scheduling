package sketcher.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.repository.UserRepositoryCustom;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @RequestMapping(value = "/schedule_upd_list", method = RequestMethod.GET)
    public String schedule_upd_list(HttpServletRequest request) {
        return "request/schedule_upd_list";
    }

    @RequestMapping(value = "/withdrawal_req_list", method = RequestMethod.GET)
    public String withdrawal_req_list(HttpServletRequest request) {
        return "request/withdrawal_req_list";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String calendar(HttpServletRequest request) {
        return "full-calendar/calendar";
    }

    @RequestMapping(value = "/calendar_admin", method = RequestMethod.GET)
    public String calendar_admin(HttpServletRequest request) {
        return "full-calendar/calendar-admin";
    }

    @RequestMapping(value = "/calendar_admin_update", method = RequestMethod.GET)
    public String calendar_admin_update(HttpServletRequest request) {
        return "full-calendar/calendar-admin-update";
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
}