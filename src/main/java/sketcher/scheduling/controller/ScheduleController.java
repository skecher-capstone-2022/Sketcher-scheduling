//package sketcher.scheduling.controller;
//
//import io.swagger.annotations.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import sketcher.scheduling.domain.Schedule;
//import sketcher.scheduling.dto.ScheduleDto;
//import sketcher.scheduling.service.ScheduleService;
//import sketcher.scheduling.service.UserService;
//
//import java.util.List;
//
//
////@ApiResponses({
////        @ApiResponse(code = 200, message = "성공 !"),
////        @ApiResponse(code = 400, message = "Bad Request !"),
////        @ApiResponse(code = 500, message = "Internal Server Error")
////})
//@Api(tags = {"스케줄 로직 API "})
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/schedule")
//public class ScheduleController {
//
//    private final ScheduleService scheduleService;
//
//    @ApiOperation(value = "스케줄 입력 GET")
//    @GetMapping("/scheduleForm")
//    public String addForm(){
//        return "/schedule/scheduleForm";
//    }
//
//    @ApiOperation(value = "스케줄 입력")
//    @PostMapping("/scheduleForm")
//    public String createForm(@ModelAttribute ScheduleDto schedule , Model model){
//        scheduleService.saveSchedule(schedule);
//
//        return "redirect:/";
//    }
//
//    @ApiOperation(value = "스케줄 조회")
//    @GetMapping
//    public String findAll(Model model){
//        List<Schedule> scheduleList = scheduleService.findAll();
//        model.addAttribute("schedules", scheduleList);
//        return "/schedule/createSchedule";
//    }
//}
