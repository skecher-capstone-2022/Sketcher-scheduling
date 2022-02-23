package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserSearchCondition;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    private final UserRepositoryCustom userRepositoryCustom;

//    @RequestMapping(value = "/all_manager_list", method = RequestMethod.GET)
//    public String all_manager_list(HttpServletRequest request) {
//        return "manager/all_manager_list";
//    }

    @RequestMapping(value = "/all_manager_list", method= {RequestMethod.GET, RequestMethod.POST})
    public String all_manager_list(
            Model model,
//            @RequestParam(required = false, defaultValue = "") UserSearchCondition condition,
            @RequestParam(required = false, defaultValue = "managerScore") String list_align,
            @RequestParam(required = false, defaultValue = "") String type, //, defaultValue = "username"
            @RequestParam(required = false, defaultValue = "") String keyword, //, defaultValue = ""
            @PageableDefault Pageable pageable) {

        UserSearchCondition condition = new UserSearchCondition(list_align, type, keyword);
        Page<User> users = userService.findAllManager(condition, pageable);
        model.addAttribute("users", users);
        model.addAttribute("condition", condition);
        return "manager/all_manager_list";
    }

    @RequestMapping(value = "/manager_detail/{userId}", method = RequestMethod.GET)
    public String manager_detail(Model model, @PathVariable(value="userId") String id) {
        List<ManagerHopeTime> details = userService.findDetailById(id);
        ArrayList<String> hope = new ArrayList<>();
        for (ManagerHopeTime detail : details) {
            switch (detail.getStart_time()) {
                case 0:
                    hope.add("새벽 0시 ~ 6시");
                    break;

                case 6:
                    hope.add("오전 6시 ~ 12시");
                    break;

                case 12:
                    hope.add("오후 12시 ~ 18시");
                    break;

                case 18:
                    hope.add("저녁 18시 ~ 24시");
                    break;
            }
        }
        model.addAttribute("details", details);
        model.addAttribute("hope", hope);
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

}
