package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ManagerAssignScheduleService managerAssignScheduleService;


//
//    @NonNull
//    private final BCryptPasswordEncoder passwordEncoder;


    @GetMapping(value = "/login")
    public String loginView(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/login";
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
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
    public String signup(UserDto joinUser, RedirectAttributes redirectAttributes) {
        userService.saveUser(joinUser);
        redirectAttributes.addAttribute("userid", joinUser.getId());

        return "redirect:/check_hopetime";
    }

    @RequestMapping(value = "/user/idCheck", method = RequestMethod.GET)
    @ResponseBody
    public boolean idCheck(@RequestParam("userid") String user_id) {
        return userService.userIdCheck(user_id);
        //true : 아이디가 존재하지 않을 때
        //false : 아이디가 이미 존재할 때
    }

    @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
    public String all_manager_list(Model model,
//            @RequestParam(required = false, defaultValue = "") UserSearchCondition condition,
                                   @RequestParam(required = false, defaultValue = "managerScore") String align,
                                   @RequestParam(required = false, defaultValue = "") String type,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   @PageableDefault Pageable pageable) {

        UserSearchCondition condition = new UserSearchCondition(align, type, keyword);
        Page<UserDto> users = userService.findAllManager(condition, pageable);
        model.addAttribute("condition", condition);
        model.addAttribute("users", users);
        return "manager/all_manager_list";
    }


    @RequestMapping(value = "/work_manager_list", method = RequestMethod.GET)
    public String work_manager_list(Model model,
//            @RequestParam(required = false, defaultValue = "") UserSearchCondition condition,
                                    @RequestParam(required = false, defaultValue = "managerScore") String align,
                                    @RequestParam(required = false, defaultValue = "") String type,
                                    @RequestParam(required = false, defaultValue = "") String keyword,
                                    @PageableDefault Pageable pageable) {

        UserSearchCondition condition = new UserSearchCondition(align, type, keyword);
        Page<UserDto> users = userService.findWorkManager(condition, pageable);
        model.addAttribute("condition", condition);
        model.addAttribute("users", users);
        return "manager/work_manager_list";
    }

    @RequestMapping(value = "/manager_detail/{userId}", method = RequestMethod.GET)
    public String manager_detail(Model model, @PathVariable(value = "userId") String id) {
        Optional<User> users = userService.findById(id);
        ArrayList<String> hope = userService.findHopeTimeById(id);

        List<ManagerAssignSchedule> schedules = managerAssignScheduleService.findByUserId(id);

        model.addAttribute("users", users);
        model.addAttribute("hope", hope);
        model.addAttribute("schedules", schedules);
        return "manager/manager_detail";
    }

    @RequestMapping(value = "/withdrawalUserDetail", method = RequestMethod.GET)
    public void withdrawalUserDetail(HttpServletResponse response,
                                     @RequestParam String userid) throws IOException {
        User user = userService.findById(userid).orElseThrow(() -> new UsernameNotFoundException(userid));
        userService.userSetNull(user);

        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("history.go(-2)");
        out.println("</script>");

        out.flush();
    }

    @RequestMapping(value = "/authUpdateUserDetail", method = RequestMethod.GET)
    public void authUpdateUserDetail(HttpServletResponse response,
                                     @RequestParam String userid) throws IOException {
        if (userid != null) {
            User user = userService.loadUserByUsername(userid);
            userService.updateAuthRole(user);
        }

        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("history.go(-2)");
        out.println("</script>");

        out.flush();
    }

    @RequestMapping(value = "/admin_mypage", method = RequestMethod.GET)
    public String admin_mypage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userSession = (User) authentication.getPrincipal();

        User user = userService.findById(userSession.getId()).get();

        model.addAttribute("user", user);
        return "mypage/admin_mypage";
    }


    @RequestMapping(value = "/manager_mypage", method = {RequestMethod.GET, RequestMethod.POST})
    public String manager_mypage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userSession = (User) authentication.getPrincipal();

        User user = userService.findById(userSession.getId()).get();
        ArrayList<String> hope = userService.findHopeTimeById(userSession.getId());

        model.addAttribute("user", user);
        model.addAttribute("hope", hope);
        return "mypage/manager_mypage";
    }

    @RequestMapping(value = "/withdrawal_req_list", method = RequestMethod.GET)
    public String withdrawal_req_list(Model model,
                                      @RequestParam(required = false, defaultValue = "managerScore") String align,
                                      @RequestParam(required = false, defaultValue = "") String type,
                                      @RequestParam(required = false, defaultValue = "") String keyword) {
        UserSearchCondition condition = new UserSearchCondition(align, type, keyword);
        List<User> users = userService.withdrawalManagers(condition);
        model.addAttribute("condition", condition);
        model.addAttribute("users", users);
        return "request/withdrawal_req_list";
    }

    //회원탈퇴
    @RequestMapping(value = "/withdrawalUser", method = RequestMethod.GET)
    public String withdrawalUser(@RequestParam(value = "userid") String userid) {
        User user = userService.findById(userid).orElseThrow(() -> new UsernameNotFoundException(userid));
        userService.userSetNull(user);

        return "redirect:/withdrawal_req_list";
    }

    @RequestMapping(value = "/dropoutReq", method = RequestMethod.POST)
    public String updateDropoutReq(@RequestParam String userid) {
        if (userid != null) {
            User user = userService.loadUserByUsername(userid);
            userService.updateDropoutReqCheck(user);

            if (user.getAuthRole().equals("MANAGER")) {
                return "redirect:/manager_mypage";
            } else if (user.getAuthRole().equals("ADMIN")) {
                return "redirect:/admin_mypage";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/dropout", method = RequestMethod.POST)
    public String updateDropout(@RequestParam String userid) {
        if (userid != null) {
            User user = userService.findById(userid).orElseThrow(() -> new UsernameNotFoundException(userid));
            userService.userSetNull(user);
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
    public String updateAdmin(@RequestParam String userid,
                              @RequestParam String userTel) {
        if (userid != null) {
            User user = userService.loadUserByUsername(userid);
            userService.updateUserTel(user, userTel);
        }
        return "redirect:/admin_mypage";
    }
}
