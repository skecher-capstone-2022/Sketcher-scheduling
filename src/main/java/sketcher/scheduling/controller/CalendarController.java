package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleService;
import sketcher.scheduling.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/full-calendar")
public class CalendarController {

    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    private final ManagerAssignScheduleService managerAssignScheduleService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/calendar-admin")
    @ResponseBody
    public List<Map<String, Object>> monthPlan() {
        List<Schedule> list = scheduleService.findAll();
        List<String> backList = new ArrayList<>();

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();
        HashMap<String, Object> backColor = new HashMap<>();
        backList.add("#FF0000");
        backList.add("#00FF00");
        backList.add("#FF69B4");
        backList.add("#00FF7F");

        for (int i = 0; i < list.size(); i++) {
            hash.put("title", list.get(i).getId());
            hash.put("start", list.get(i).getScheduleDateTimeStart());
            hash.put("end", list.get(i).getScheduleDateTimeEnd());
            hash.put("color", backList.get(i).toString());
//            hash.put("color", "#00FF00");
//            hash.put("color", "#FF69B4");
//            hash.put("startTime", list.get(i).getStart_time());
//            hash.put("endTime", list.get(i).getFinish_time());


            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;

    }


    @PostMapping("/calendar-admin-update")
    @ResponseBody
    public String addEvent(@RequestBody List<Map<String, Object>> param) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (int i = 0; i < param.size(); i++) {

            String username = (String) param.get(i).get("title");
            String startDateString = (String) param.get(i).get("start");
            String endDateString = (String) param.get(i).get("end");

            LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

            UserDto userDto = UserDto.builder()
                    .username(username)
                    .build();

            String user = userService.saveUser(userDto);
            User userEntity = userRepository.findByUsername(user).get();

            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .scheduleDateTimeStart(startDate)
                    .scheduleDateTimeEnd(endDate)
                    .build();

            Integer scheduleId = scheduleService.saveSchedule(scheduleDto);
            Schedule scheduleEntity = scheduleRepository.findById(scheduleId).get();

            ManagerAssignScheduleDto assignScheduleDto = ManagerAssignScheduleDto.builder()
                    .user(userEntity)
                    .schedule(scheduleEntity)
                    .build();

            managerAssignScheduleService.saveManagerAssignSchedule(assignScheduleDto);

        }
        return "/full-calendar/calendar-admin-update";
    }

    @DeleteMapping("/calendar-admin-update")
    @ResponseBody
    public String deleteEvent(@RequestBody List<Map<String, Object>> param){



        for (int i = 0; i < param.size(); i++) {
            String username = (String) param.get(i).get("title");

            managerAssignScheduleService.deleteById(username);
        }


        return "/full-calendar/calendar-admin-update";
    }


//        JSONObject jsonObj = new JSONObject();
//        JSONArray jsonArr = new JSONArray();
//
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//    @PostMapping("/calendar-admin-update")
//    @ResponseBody
//    public List<Map<String, Object>> addEvent(Map<String, Object> param){
//        List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
//        Map<String, Object> params = new HashMap<String, Object>();
//
}
