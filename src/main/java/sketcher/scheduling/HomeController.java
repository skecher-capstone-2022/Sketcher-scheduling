package sketcher.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

//    @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
//    public String all_manager_list(HttpServletRequest request) {
//        return "manager/all_manager_list";
//    }

    @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
    public String all_manager_list(Model model, @PageableDefault Pageable pageable) {

//        SearchParam searchParam = setSearchParameter(searchType, searchValue);

        Page<User> users = userService.findAllManager(pageable);
        model.addAttribute("users", users);



//        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
//                users.getTotalElements(), users.getTotalPages(), users.getSize(),
//                users.getNumber(), users.getNumberOfElements());

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