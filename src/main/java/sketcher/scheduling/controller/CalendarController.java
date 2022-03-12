package sketcher.scheduling.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Api(tags = {"스케줄 로직 API "})
@Controller
@RequiredArgsConstructor
@RequestMapping("/full-calendar")
public class CalendarController {
// 수정
    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);

    private final ManagerAssignScheduleService managerAssignScheduleService;
    private final UserService userService;

    /**
     * calendar 페이지 개별 조회
     */
    @ApiOperation(value = "스케줄 개별 조회")
    @GetMapping("/calendar")
    @ResponseBody
    public List<Map<String, Object>> showEachEvent(Principal principal) throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        String username = principal.getName();

        if (! principal.getName().isEmpty()) {
            User user = userService.findByUsername(username).get();
            List<ManagerAssignSchedule> byUser = managerAssignScheduleService.findByUser(user);

            for (ManagerAssignSchedule managerAssignSchedule : byUser) {
                hash.put("title", managerAssignSchedule.getUser().getUsername());
                hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
                hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

                jsonObj = new JSONObject(hash);
                jsonArr.add(jsonObj);
            }
        }
        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }

    /**
     * calendar-admin 페이지 조회
     */
    @ApiOperation(value = "스케줄 전체 조회")
    @GetMapping("/calendar-admin")
    @ResponseBody
    public List<Map<String, Object>> showAllEvent() throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<String> color = new ArrayList<>();
        color.add("red");
        color.add("green");
        color.add("yellow");
        color.add("blue");
        color.add("orange");

        List<ManagerAssignSchedule> list = managerAssignScheduleService.findAll();
        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }

        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }

    /**
     * calendar-admin-update 페이지 조회
     */
    @ApiOperation(value = "스케줄 수정 조회")
    @GetMapping("/calendar-admin-update")
    @ResponseBody
    public List<Map<String, Object>> showAllEventInUpdate() throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<String> color = new ArrayList<>();
        color.add("#343a40");
        color.add("green");
        color.add("purple");
        color.add("blue");
        color.add("orange");

        List<ManagerAssignSchedule> list = managerAssignScheduleService.findAll();

        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }


        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }

    /**
     * calendar-admin-update 페이지 이벤트 생성
     */
    @ApiOperation(value = "스케줄 생성")
    @PostMapping("/calendar-admin-update")
    @ResponseBody
    public String addEvent(@RequestBody List<Map<String, Object>> param) throws RuntimeException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (Map<String, Object> list : param) {

            String eventName = (String) list.get("title"); // 이름 받아오기
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");
//            String backgroundColor = (String) list.get("backgroundColor");
//
//import lombok.RequiredArgsConstructor;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import sketcher.scheduling.domain.ManagerAssignSchedule;
//import sketcher.scheduling.domain.Schedule;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.ManagerAssignScheduleDto;
//import sketcher.scheduling.dto.ScheduleDto;
//import sketcher.scheduling.dto.UserDto;
//import sketcher.scheduling.repository.ScheduleRepository;
//import sketcher.scheduling.repository.UserRepository;
//import sketcher.scheduling.service.ManagerAssignScheduleService;
//import sketcher.scheduling.service.ScheduleService;
//import sketcher.scheduling.service.UserService;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/full-calendar")
//public class CalendarController {
//
//    private static final Logger log = LoggerFactory.getLogger(CalendarController.class);
//
//    private final ScheduleService scheduleService;
//    private final ScheduleRepository scheduleRepository;
//    private final ManagerAssignScheduleService managerAssignScheduleService;
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    @GetMapping("/calendar-admin")
//    @ResponseBody
//    public List<Map<String, Object>> monthPlan() {
//        List<Schedule> list = scheduleService.findAll();
//        List<String> backList = new ArrayList<>();
//
//        JSONObject jsonObj = new JSONObject();
//        JSONArray jsonArr = new JSONArray();
//
//        HashMap<String, Object> hash = new HashMap<>();
//        HashMap<String, Object> backColor = new HashMap<>();
//        backList.add("#FF0000");
//        backList.add("#00FF00");
//        backList.add("#FF69B4");
//        backList.add("#00FF7F");
//
//        for (int i = 0; i < list.size(); i++) {
//            hash.put("title", list.get(i).getId());
//            hash.put("start", list.get(i).getScheduleDateTimeStart());
//            hash.put("end", list.get(i).getScheduleDateTimeEnd());
//            hash.put("color", backList.get(i).toString());
////            hash.put("color", "#00FF00");
////            hash.put("color", "#FF69B4");
////            hash.put("startTime", list.get(i).getStart_time());
////            hash.put("endTime", list.get(i).getFinish_time());
//
//
//            jsonObj = new JSONObject(hash);
//            jsonArr.add(jsonObj);
//        }
//        log.info("jsonArrCheck: {}", jsonArr);
//        return jsonArr;
//
//    }
//
//
//    @PostMapping("/calendar-admin-update")
//    @ResponseBody
//    public String addEvent(@RequestBody List<Map<String, Object>> param) throws Exception {
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
//
//        for (int i = 0; i < param.size(); i++) {
//
//            String username = (String) param.get(i).get("title");
//            String startDateString = (String) param.get(i).get("start");
//            String endDateString = (String) param.get(i).get("end");
//
//            LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
//            LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
//
//            UserDto userDto = UserDto.builder()
//                    .username(username)
//                    .build();
//
//            String user = userService.saveUser(userDto);
//            User userEntity = userRepository.findByUsername(user).get();
//
//            ScheduleDto scheduleDto = ScheduleDto.builder()
//                    .scheduleDateTimeStart(startDate)
//                    .scheduleDateTimeEnd(endDate)
//                    .build();
//
//            Integer scheduleId = scheduleService.saveSchedule(scheduleDto);
//            Schedule scheduleEntity = scheduleRepository.findById(scheduleId).get();
//
//            ManagerAssignScheduleDto assignScheduleDto = ManagerAssignScheduleDto.builder()
//                    .user(userEntity)
//                    .schedule(scheduleEntity)
//                    .build();
//
//            managerAssignScheduleService.saveManagerAssignSchedule(assignScheduleDto);
//
//        }
//        return "/full-calendar/calendar-admin-update";
//    }
//
//    @DeleteMapping("/calendar-admin-update")
//    @ResponseBody
//    public String deleteEvent(@RequestBody List<Map<String, Object>> param){
//
//        for (int i = 0; i < param.size(); i++) {
//            String username = (String) param.get(i).get("title");
//
//            User user = userService.findByUsername(username).get();
//            managerAssignScheduleService.deleteByUser(user);
//        }
//
//
//        return "/full-calendar/calendar-admin-update";
//    }
//
//    @PatchMapping("/calendar-admin-update")
//    @ResponseBody
//    public String modifyEvent(@RequestBody List<Map<String, Object>> param){
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
//
//        for (int i = 0; i < param.size(); i++) {
//
//            String username = (String) param.get(i).get("title");
//            String startDateString = (String) param.get(i).get("start");
//            String endDateString = (String) param.get(i).get("end");
//
//            LocalDateTime modifiedstartDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
//            LocalDateTime modifiedendDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
//
//            ScheduleDto scheduleDto = ScheduleDto.builder()
//                    .scheduleDateTimeStart(modifiedstartDate)
//                    .scheduleDateTimeEnd(modifiedendDate)
//                    .build();
//
//            Integer scheduleId = scheduleService.saveSchedule(scheduleDto);
//            Schedule scheduleEntity = scheduleRepository.findById(scheduleId).get();
//
//            ManagerAssignScheduleDto assignScheduleDto = ManagerAssignScheduleDto.builder()
//                    .schedule(scheduleEntity)
//                    .build();
//
//            managerAssignScheduleService.saveManagerAssignSchedule(assignScheduleDto);
//        }
//        return "/full-calendar/calendar-admin-update";
//        }
//}
