package sketcher.scheduling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goHome(HttpServletRequest request) {
        return "content/home";
    }

    @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
    public String goHome2(HttpServletRequest request) {
        return "manager/all_manager_list";
    }

    @RequestMapping(value = "/manager_detail", method = RequestMethod.GET)
    public String goHome3(HttpServletRequest request) {
        return "manager/manager_detail";
    }

    @RequestMapping(value = "/work_manager_list", method = RequestMethod.GET)
    public String goHome4(HttpServletRequest request) {
        return "manager/work_manager_list";
    }

    @RequestMapping(value = "/admin_mypage", method = RequestMethod.GET)
    public String goHome5(HttpServletRequest request) {
        return "mypage/admin_mypage";
    }

    @RequestMapping(value = "/manager_mypage", method = RequestMethod.GET)
    public String goHome6(HttpServletRequest request) {
        return "mypage/manager_mypage";
    }

    @RequestMapping(value = "/schedule_upd_list", method = RequestMethod.GET)
    public String goHome7(HttpServletRequest request) {
        return "request/schedule_upd_list";
    }

    @RequestMapping(value = "/withdrawal_req_list", method = RequestMethod.GET)
    public String goHome8(HttpServletRequest request) {
        return "request/withdrawal_req_list";
    }

    @RequestMapping(value = "/calendar_admin", method = RequestMethod.GET)
    public String goHome9(HttpServletRequest request) {
        return "full-calendar/calendar-admin";
    }

    @RequestMapping(value = "/calendar_admin_update", method = RequestMethod.GET)
    public String goHome10(HttpServletRequest request) {
        return "full-calendar/calendar-admin-update";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String goHome11(HttpServletRequest request) {
        return "full-calendar/calendar";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        return "user/login";
    }

//    step3
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