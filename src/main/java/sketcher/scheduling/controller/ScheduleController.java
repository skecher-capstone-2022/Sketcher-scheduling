package sketcher.scheduling.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.service.ScheduleService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/scheduleForm")
    public String addForm(){
        return "/schedule/scheduleForm";
    }

    @PostMapping("/scheduleForm")
    public String createForm(@ModelAttribute ScheduleDto schedule){
        scheduleService.saveSchedule(schedule);
        return "redirect:/";
    }
}
