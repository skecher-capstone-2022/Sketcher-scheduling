package sketcher.scheduling.controller;

//import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.domain.ScheduleUpdateReq;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.ScheduleDto;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.ScheduleRepository;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;
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
    private final ScheduleUpdateReqService updateReqService;




    @ApiOperation(value = "스케줄 개별 조회")
    @GetMapping("/calendar")
    @ResponseBody
    public List<Map<String, Object>> showEachEvent(Authentication authentication) throws Exception {

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();
        List<String> color = getColor();


        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        System.out.println("==================================");
        System.out.println("principal = " + principal);
        System.out.println("username = " + username);
        if (!username.isEmpty()) {
            User user = userService.findByUsername(username).get();
            List<ManagerAssignSchedule> byUser = managerAssignScheduleService.findByUser(user);

            System.out.println("================================");
            System.out.println("byUser = " + byUser);
            for (ManagerAssignSchedule managerAssignSchedule : byUser) {
                hash.put("title", managerAssignSchedule.getUser().getUsername());
                hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
                hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());


                User userCode = userService.findByUsername(hash.get("title").toString()).get();
                Integer code = userCode.getCode();
                if(code / 10 > 0)
                    code = code % 10;
                if (user != null) {
                    hash.put("backgroundColor", color.get(code));
                }

                jsonObj = new JSONObject(hash);
                jsonArr.add(jsonObj);
            }

        }

        log.info("jsonArrCheck: {}", jsonArr);
        return jsonArr;
    }




    @PostMapping("/calendar")
    @ResponseBody
    public String sendModifyRequest(@RequestBody List<Map<String, Object>> param) {

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

            ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.findByUserAndScheduleDateTimeStartAndScheduleDateTimeEnd(user, oldStart, oldEnd).get();
//            Integer assignScheduleId = managerAssignSchedule.getId();
            //assignid 가 updatereq 에 없는 경우에만 요청을 보냄
            Optional<ScheduleUpdateReq> updateCheck = updateReqService.findByAssignSchedule(managerAssignSchedule);
            System.out.println("==========================================");
            System.out.println("updateCheck = " + updateCheck);
                if (!updateCheck.isPresent()) {
                    ScheduleUpdateReqDto scheduleUpdateReqDto = ScheduleUpdateReqDto.builder()
                            .assignSchedule(managerAssignSchedule)
                            .changeDate(modifiedStartDate)
                            .build();
                    updateReqService.saveScheduleUpdateReq(scheduleUpdateReqDto);
                }else if(updateCheck.isPresent()){
//                        try{
//                            throw new Exception("수정은 한번만 가능합니다.");
//                        } catch (Exception e) {
//                            ScheduleUpdateReqDto scheduleUpdateReqDto2 = ScheduleUpdateReqDto.builder()
//                                    .assignSchedule(null)
//                                    .build();
//                            updateReqService.saveScheduleUpdateReq(scheduleUpdateReqDto2);
//                            System.out.println("수정 오류 : " + e.getMessage());
//                            e.printStackTrace();
                    ScheduleUpdateReqDto scheduleUpdateReqDtoUpdate = ScheduleUpdateReqDto.builder()
                            .assignSchedule(managerAssignSchedule)
                            .changeDate(modifiedStartDate)
                            .build();
                    updateReqService.duplicateUpdateRequest(updateCheck.get().getId(),scheduleUpdateReqDtoUpdate );
//                        }
                }

        }
        return "/full-calendar/calendar";
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

        List<String> color = getColor();


        List<ManagerAssignSchedule> list = managerAssignScheduleService.findAll();

        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            User user = userService.findByUsername(hash.get("title").toString()).get();
            Integer code = user.getCode();
            if(code / 10 > 0)
                code = code % 10;
            if (user != null) {
                hash.put("backgroundColor", color.get(code));
            }

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

        List<String> color = getColor();

        List<ManagerAssignSchedule> list = managerAssignScheduleService.findAll();

        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            User user = userService.findByUsername(hash.get("title").toString()).get();
            Integer code = user.getCode();
            if(code / 10 > 0)
                code = code % 10;
            if (user != null) {
                hash.put("backgroundColor", color.get(code));
            }
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
                //                User user = userService.findById(eventName).get();
//                String username = user.getId();

                System.out.println("username = " + username);
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
//                if (eventName.equals(username)) {
//
//                    ManagerAssignScheduleDto managerAssignScheduleDto = ManagerAssignScheduleDto.builder()
//                            .user(user)
//                            .scheduleDateTimeStart(startDate)
//                            .scheduleDateTimeEnd(endDate)
//                            .build();
//
//                    managerAssignScheduleService.saveManagerAssignSchedule(managerAssignScheduleDto);
//                }
            } catch (NoSuchElementException e) {
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

//            User user = userService.findById(eventName).get();
//            String username = user.getId();

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

//            User user = userService.findById(eventName).get();
//            String username = user.getId();

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

    private List<String> getColor() {
        List<String> color = new ArrayList<>();
        color.add("#FFEBCD");
        color.add("#FA8072");
        color.add("#FF7F50");
        color.add("#FFD700");
        color.add("#B0E0E6");
        color.add("#48D1CC");
        color.add("#9370DB");
        color.add("#FF69B4");
        color.add("#FFB6C1");
        color.add("#00BFFF");
        return color;
    }
}
