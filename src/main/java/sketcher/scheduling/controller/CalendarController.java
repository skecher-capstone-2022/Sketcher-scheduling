package sketcher.scheduling.controller;

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
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

    /**
     * calendar-admin 페이지 조회
     */
    @GetMapping("/calendar-admin")
    @ResponseBody
    public List<Map<String, Object>> showAllEvent() throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<Schedule> list = scheduleService.findAll();

        for (Schedule schedule : list) {
            hash.put("title", schedule.getCreator_id());
            hash.put("start", schedule.getScheduleDateTimeStart());
            hash.put("end", schedule.getScheduleDateTimeEnd());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }

    /**
     * calendar-admin-update 페이지 조회
     */
    @GetMapping("/calendar-admin-update")
    @ResponseBody
    public List<Map<String, Object>> showAllEventInUpdate() throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<Schedule> list = scheduleService.findAll();

        for (Schedule schedule : list) {
            hash.put("title", schedule.getCreator_id());
            hash.put("start", schedule.getScheduleDateTimeStart());
            hash.put("end", schedule.getScheduleDateTimeEnd());

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }
    
    /**
     * calendar-admin-update 페이지 이벤트 생성
     */
    @PostMapping("/calendar-admin-update")
    @ResponseBody
    public String addEvent(@RequestBody List<Map<String, Object>> param) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (Map<String, Object> list : param) {

            String eventName = (String) list.get("title"); // 이름 받아오기
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");

            LocalDateTime startDateUTC = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDateUTC = LocalDateTime.parse(endDateString, dateTimeFormatter);

            LocalDateTime startDate = startDateUTC.plusHours(9);
            LocalDateTime endDate = endDateUTC.plusHours(9);

            User user = userService.findByUsername(eventName).get();
            String username = user.getUsername();


            System.out.println("=================================");
            System.out.println("startDate = " + startDate);
            /**
             * exception 처리를 통해 존재하지 않는 경우 alert 필요
             */
            System.out.println("username = " + username);
            System.out.println("eventName = " + eventName);

            if (eventName.equals(username)) {
                ScheduleDto scheduleDto = ScheduleDto.builder()
                        .creator_id(eventName)
                        .scheduleDateTimeStart(startDate)
                        .scheduleDateTimeEnd(endDate)
                        .build();

                Integer scheduleId = scheduleService.saveSchedule(scheduleDto);
                Schedule scheduleEntity = scheduleService.findById(scheduleId).get();

                ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                        .user(user)
                        .schedule(scheduleEntity)
                        .build();

                managerAssignScheduleService.saveManagerAssignSchedule(managerAssignScheduleDto);
            }
        }
        return "/full-calendar/calendar-admin-update";
    }

    /**
     * calendar-admin-update 페이지 이벤트 삭제
     */
    @DeleteMapping("/calendar-admin-update")
    @ResponseBody
    public String deleteEvent(@RequestBody List<Map<String, Object>> param) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (Map<String, Object> list : param) {

            System.out.println("list = " + list);

            String eventName = (String) list.get("title"); // 이름 받아오기
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");

            System.out.println("startDateString = " + startDateString);

            LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

            System.out.println("startDate = " + startDate);

            User user = userService.findByUsername(eventName).get();
            String username = user.getUsername();

//            Optional<Schedule> schedules = scheduleService.findByScheduleDateTimeStart(startDate);
            Schedule schedule = scheduleService.findByScheduleDateTimeStartAndScheduleDateTimeEnd(startDate, endDate).get();
            Integer scheduleId = schedule.getId();
            System.out.println("=================================");
            System.out.println("scheduleId = " + scheduleId);

            ManagerAssignSchedule assignSchedule = managerAssignScheduleService.findBySchedule(schedule).get();
            Integer assignScheduleId = assignSchedule.getId();
            System.out.println("========================");
            System.out.println("assignSchedule = " + assignSchedule);
            System.out.println("assignScheduleId = " + assignScheduleId);

            /**
             * exception 처리를 통해 존재하지 않는 경우 alert 필요
             */

            if (eventName.equals(username)) {
//                System.out.println("success");
//                scheduleService.deleteById(scheduleId);
//                managerAssignScheduleService.deleteById(assignScheduleId);
                managerAssignScheduleService.deleteBySchedule(schedule);
            }
        }
        return "/full-calendar/calendar-admin-update";
    }

    /**
     * 취소를 누름에도 데이터 파싱. 체크해서 없애기.
     */
    /**
     * calendar-admin-update 페이지 이벤트 생성
     */
    @PatchMapping("/calendar-admin-update")
    @ResponseBody
    public String modifyEvent(@RequestBody List<Map<String, Object>> param) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);


        for (Map<String, Object> list : param) {

            String eventName = (String) list.get("title"); // 이름 받아오기
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");

            String oldTitle = (String) list.get("oldTitle");
            String oldStartString = (String) list.get("oldStart");
            String oldEndString = (String) list.get("oldEnd");

            LocalDateTime modifiedStartDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime modifiedEndDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
            LocalDateTime oldStart = LocalDateTime.parse(oldStartString, dateTimeFormatter);
            LocalDateTime oldEnd = LocalDateTime.parse(oldEndString, dateTimeFormatter);

            System.out.println("=====================================");
            System.out.println("modifiedStartDate = " + modifiedStartDate);
            System.out.println("modifiedEndDate = " + modifiedEndDate);
            System.out.println("oldTitle = " + oldTitle);
            System.out.println("oldStart = " + oldStart);
            System.out.println("oldEnd = " + oldEnd);

            Schedule schedule = scheduleService.findByScheduleDateTimeStartAndScheduleDateTimeEnd(oldStart, oldEnd).get();
            Integer scheduleId = schedule.getId();


            System.out.println("=====================================");
            System.out.println("scheduleId = " + scheduleId);
            if (scheduleId != null) {

                ScheduleDto scheduleDto = ScheduleDto.builder()
                        .scheduleDateTimeStart(modifiedStartDate)
                        .scheduleDateTimeEnd(modifiedEndDate)
                        .build();

                scheduleService.update(scheduleId, scheduleDto);
            }
        }
        return "/full-calendar/calendar-admin-update";
    }
}
