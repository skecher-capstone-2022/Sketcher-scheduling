package sketcher.scheduling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController { @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
    public String all_manager_list(HttpServletRequest request) {
        return "manager/all_manager_list";
    }

    @RequestMapping(value = "/manager_detail", method = RequestMethod.GET)
    public String manager_detail(HttpServletRequest request) {
        return "manager/manager_detail";
    }

    @RequestMapping(value = "/work_manager_list", method = RequestMethod.GET)
    public String work_manager_list(HttpServletRequest request) {
        return "manager/work_manager_list";
    }

    @RequestMapping(value = "/admin_mypage", method = RequestMethod.GET)
    public String admin_mypage(HttpServletRequest request) {
        return "mypage/admin_mypage";
    }

    @RequestMapping(value = "/manager_mypage", method = RequestMethod.GET)
    public String manager_mypage(HttpServletRequest request) {
        return "mypage/manager_mypage";
    }

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


}