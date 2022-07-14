package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UpdateReqController {

    private final ScheduleUpdateReqService updateReqService;
    private final ManagerAssignScheduleService assignScheduleService;

    @RequestMapping(value = "/schedule_upd_list", method = RequestMethod.GET)
    public String schedule_upd_list(Model model,
                                    @RequestParam(value="list_align", required = false, defaultValue = "req_date_desc") String sort) {
        List<ManagerAssignSchedule> updateReqList = assignScheduleService.findUpdateReqIdIsNotNull();
        model.addAttribute("updateReqList", updateReqList);

        return "request/schedule_upd_list";
    }

    @RequestMapping(value = "/acceptRequest")
    public String acceptRequest(@RequestParam(value="chkList",required=true) List<Integer> requestId){

        for (Integer id : requestId) {
            updateReqService.acceptReq(id);
        }
        return "redirect:schedule_upd_list";
    }

}
