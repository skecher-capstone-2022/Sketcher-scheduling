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
//            System.out.println(" ================================ ");
//            System.out.println("backgroundColor = " + backgroundColor);

            LocalDateTime startDateUTC = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDateUTC = LocalDateTime.parse(endDateString, dateTimeFormatter);

            LocalDateTime startDate = startDateUTC.plusHours(9);
            LocalDateTime endDate = endDateUTC.plusHours(9);
            try {

                User user = userService.findByUsername(eventName).get();
                String username = user.getUsername();

                /**
                 * exception 처리를 통해 존재하지 않는 경우 alert 필요
                 */

                if (eventName.equals(username)) {

                    ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                            .user(user)
                            .scheduleDateTimeStart(startDate)
                            .scheduleDateTimeEnd(endDate)
                            .build();

                    managerAssignScheduleService.saveManagerAssignSchedule(managerAssignScheduleDto);
                }
            }catch (NoSuchElementException e){
                throw new NoSuchElementException("매니저가 존재하지 않습니다.");
            }
        }

        return "/full-calendar/calendar-admin-update";
    }

    /**
     * calendar-admin-update 페이지 이벤트 삭제
     */
    @ApiOperation(value = "스케줄 삭제")
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

            ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(user, startDate, endDate).get();
            Integer assignScheduleId = managerAssignSchedule.getId();

            if (eventName.equals(username)) {
                managerAssignScheduleService.deleteById(assignScheduleId);
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
    @ApiOperation(value = "스케줄 수정")
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

            User user = userService.findByUsername(eventName).get();
            String username = user.getUsername();

            ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(user, oldStart, oldEnd).get();
            Integer assignScheduleId = managerAssignSchedule.getId();


            System.out.println("=====================================");
            System.out.println("assignScheduleId = " + assignScheduleId);
            if (assignScheduleId != null) {

                ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
                        .scheduleDateTimeStart(modifiedStartDate)
                        .scheduleDateTimeEnd(modifiedEndDate)
                        .build();


                managerAssignScheduleService.update(assignScheduleId, managerAssignScheduleDto);
            }
        }
        return "/full-calendar/calendar-admin-update";
    }
}
