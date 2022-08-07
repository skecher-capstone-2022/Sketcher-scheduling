package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UpdateReqController {
    private final ScheduleUpdateReqService updateReqService;
    private final ManagerAssignScheduleService assignScheduleService;

    @RequestMapping(value = "/schedule_upd_list", method = RequestMethod.GET)
    public String schedule_upd_list(Model model) {
        List<ManagerAssignSchedule> updateReqList = assignScheduleService.findAcceptReqCheckIsN();
        model.addAttribute("updateReqList", updateReqList);
        return "request/schedule_upd_list";
    }

    @RequestMapping(value = "/acceptRequest")
    public String acceptRequest(@RequestParam(value = "chkList", required = true) List<Integer> requestId) {
        for (Integer id : requestId) {
            updateReqService.acceptReq(id);
        }
        return "redirect:schedule_upd_list";
    }

}
