package sketcher.scheduling.controller;

//import com.sun.deploy.net.HttpResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.dto.ScheduleUpdateReqDto;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ScheduleUpdateReqService;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        try{
            if (!username.isEmpty()) {
                User user = userService.findByUsername(username).get();
                List<ManagerAssignSchedule> byUser = managerAssignScheduleService.findByUser(user);

                for (ManagerAssignSchedule managerAssignSchedule : byUser) {
                    hash.put("title", managerAssignSchedule.getUser().getUsername());
                    hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
                    hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

                    Integer code = managerAssignSchedule.getUser().getCode();
                    if (code / color.size() > 0)
                        code = code % color.size();
                    hash.put("backgroundColor", color.get(code));

                    jsonObj = new JSONObject(hash);
                    jsonArr.add(jsonObj);
                }
            }
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("로그인 정보가 존재하지 않습니다.");
        }

        return jsonArr;
    }


    @PostMapping("/calendar")
    @ResponseBody
    public String sendModifyRequest(@RequestBody List<Map<String, Object>> param) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (Map<String, Object> list : param) {

            String eventName = (String) list.get("title");
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");

            String oldTitle = (String) list.get("oldTitle");
            String oldStartString = (String) list.get("oldStart");
            String oldEndString = (String) list.get("oldEnd");

            LocalDateTime modifiedStartDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime modifiedEndDate = LocalDateTime.parse(endDateString, dateTimeFormatter);
            LocalDateTime oldStart = LocalDateTime.parse(oldStartString, dateTimeFormatter);
            LocalDateTime oldEnd = LocalDateTime.parse(oldEndString, dateTimeFormatter);

//            User user = userService.findByUsername(eventName).get();
            User user = userService.findByUsername(eventName).orElseThrow(() -> new Exception("입력한 매니저가 존재하지 않습니다."));

            ManagerAssignSchedule managerAssignSchedule =
                    managerAssignScheduleService.getBeforeSchedule(user, oldStart, oldEnd).get();

            if (managerFirstRequestUpdateSchedule(managerAssignSchedule)) {
                updateReqService.saveScheduleUpdateReq(managerAssignSchedule, modifiedStartDate, modifiedEndDate);
            } else {
                updateReqService.duplicateUpdateRequest(managerAssignSchedule, modifiedStartDate, modifiedEndDate);
            }

        }
        return "/full-calendar/calendar";
    }

    private boolean managerFirstRequestUpdateSchedule(ManagerAssignSchedule managerAssignSchedule) {
        return managerAssignSchedule.getUpdateReq() == null;
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


        List<ManagerAssignSchedule> list = managerAssignScheduleService.AssignScheduleFindAll();


        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            Integer code = managerAssignSchedule.getUser().getCode();
            if (code / color.size() > 0)
                code = code % color.size();
            hash.put("backgroundColor", color.get(code));

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
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

        List<ManagerAssignSchedule> list = managerAssignScheduleService.AssignScheduleFindAll();

        for (ManagerAssignSchedule managerAssignSchedule : list) {
            hash.put("title", managerAssignSchedule.getUser().getUsername());
            hash.put("start", managerAssignSchedule.getScheduleDateTimeStart());
            hash.put("end", managerAssignSchedule.getScheduleDateTimeEnd());

            Integer code = managerAssignSchedule.getUser().getCode();
            if (code / color.size() > 0)
                code = code % color.size();
            hash.put("backgroundColor", color.get(code));

            jsonObj = new JSONObject(hash);
            jsonArr.add(jsonObj);
        }
        return jsonArr;
    }

    /**
     * calendar-admin-update 페이지 이벤트 생성
     */
    @ApiOperation(value = "스케줄 생성")
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
//            try {
//                User user = userService.findByUsername(eventName).get();
                User user = userService.findByUsername(eventName).orElseThrow(() -> new Exception("입력한 매니저가 존재하지 않습니다."));
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
//            } catch (NoSuchElementException e) {
//                throw new NoSuchElementException("매니저가 존재하지 않습니다.");
//            }
        }

        return "/full-calendar/calendar-admin-update";
    }

    /**
     * calendar-admin-update 페이지 이벤트 삭제
     */
    @ApiOperation(value = "스케줄 삭제")
    @DeleteMapping("/calendar-admin-update")
    @ResponseBody
    public String deleteEvent(@RequestBody List<Map<String, Object>> param) throws Exception{

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

        for (Map<String, Object> list : param) {

            String eventName = (String) list.get("title"); // 이름 받아오기
            String startDateString = (String) list.get("start");
            String endDateString = (String) list.get("end");

            LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateString, dateTimeFormatter);

            User user = userService.findByUsername(eventName).orElseThrow(() -> new Exception("입력한 매니저가 존재하지 않습니다."));
            String username = user.getUsername();

            ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.getBeforeSchedule(user, startDate, endDate).get();
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
    public String modifyEvent(@RequestBody List<Map<String, Object>> param) throws Exception {

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

            User user = userService.findByUsername(eventName).get();

            ManagerAssignSchedule managerAssignSchedule = managerAssignScheduleService.getBeforeSchedule(user, oldStart, oldEnd)
                    .orElseThrow(() -> new Exception("해당 스케줄이 존재하지 않습니다."));
            Integer assignScheduleId = managerAssignSchedule.getId();

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

    //    @ApiOperation(value = "리모컨 조회")
    @GetMapping("/create_schedule")
    public String managerRemote(Model model) {

        List<User> allUser = userService.findAll();
        Long CalHours = 0L;
        List<Long> betweenHours = new ArrayList<>();

/**
 * 매주마다 달라지는 근무 시간에 따른 몆 주차 계산하기
 */

//        LocalDateTime today = LocalDateTime.now();
        Calendar c = Calendar.getInstance();
//        String week = String.valueOf(c.get(Calendar.WEEK_OF_MONTH));
        String year = String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
//        System.out.println("week = " + week);
        System.out.println("year = " + year);
//
//        int monthValue = today.getMonthValue();
//        System.out.println("monthValue = " + monthValue);

        for (User user : allUser) {
            List<ManagerAssignSchedule> managerAssignScheduleList = user.getManagerAssignScheduleList();
            for (ManagerAssignSchedule managerAssignSchedule : managerAssignScheduleList) {
                CalHours += ChronoUnit.HOURS.between(managerAssignSchedule.getScheduleDateTimeStart(), managerAssignSchedule.getScheduleDateTimeEnd());
            }
            betweenHours.add(CalHours);
            CalHours = 0L;
        }

        model.addAttribute("users", allUser);
        model.addAttribute("times", betweenHours);

        return "/full-calendar/create_schedule";
    }

//
    @PostMapping("/create_schedule")
    @ResponseBody
    public String getChecked(HttpServletRequest request , @RequestParam(value = "checkBoxArr[]") List<String> checkBoxArr){

//        String value[] = new String[5];
        System.out.println("checkBoxArr = " + checkBoxArr);
        if(checkBoxArr.contains("allCondition"))
            System.out.println("wow = " );
        if(checkBoxArr.contains("weekendTwoHours"))
            System.out.println("weekend = ");
        if (checkBoxArr.contains("totalWorkHours"))
            System.out.println("totalwork = ");
//        for (int i = 0; i < checkBoxArr.size(); i++) {
//            value[i] = checkBoxArr.get(i);
//            System.out.println("value = " + value);
//        }
//        System.out.println("value = " + value);
        return "/full-calendar/create_schedule";
    }

    private List<String> getColor() {
        List<String> color = new ArrayList<>();
        color.add("#FDAFAB");
        color.add("#FAF1D6");
        color.add("#FAD4AE");
        color.add("#D9F1F1");
        color.add("#FADEE1");
        color.add("#B6E3E9");
        color.add("#E5C1C5");
        color.add("#F2EEE5");
        color.add("#C3E2DD");
        color.add("#C4F2CE");
        color.add("#9FDEBD");
        color.add("#7FD9D6");
        color.add("#80CEBE");
        return color;
    }

}
