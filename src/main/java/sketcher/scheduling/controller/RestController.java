package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.algorithm.TempUser;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.ManagerAssignScheduleDto;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.UserService;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {
    private final UserRepository userRepository;
    private final ManagerHopeTimeService hopeTimeService;
    private final UserService userService;
    private final ManagerAssignScheduleService assignScheduleService;

    @GetMapping(value = "/find_All_Manager")
    public List<User> findAllManager() {
        return userRepository.findAllManager();
    }

    @GetMapping(value = "/find_All_Manager_Hope_Time")
    public List<ManagerHopeTime> findAllManagerHopeTime() {
        return hopeTimeService.findAll();
    }

    @RequestMapping(value = "/create_assign_schedule", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public int createAssignSchedule(@RequestBody List<Map<String, Object>> param) throws ParseException {
        for (Map<String, Object> stringObjectMap : param) {
            String startDateString = (String) stringObjectMap.get("startTime"); //2022-07-24T22:00:00.000Z
            String endDateString = (String) stringObjectMap.get("endTime"); //2022-07-24T22:00:00.000Z
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);

            LocalDateTime startDateUTC = LocalDateTime.parse(startDateString, dateTimeFormatter);
            LocalDateTime endDateUTC = LocalDateTime.parse(endDateString, dateTimeFormatter);

            LocalDateTime startDate = startDateUTC.plusHours(9);
            LocalDateTime endDate = endDateUTC.plusHours(9);

            ManagerAssignScheduleDto dto = ManagerAssignScheduleDto.builder()
                    .user(userService.findByCode((Integer) stringObjectMap.get("usercode")).get())
                    .scheduleDateTimeStart(startDate)
                    .scheduleDateTimeEnd(endDate)
                    .build();
            assignScheduleService.saveManagerAssignSchedule(dto);
        }

        return param.size();
    }


    @RequestMapping(value = "/current_status_info", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public int currentStatusInfo(@RequestBody List<Map<String, Object>> param) throws ParseException, org.json.simple.parser.ParseException {
        String date = "";
        String day = "";
        HashMap<Integer, TempUser> userList = new HashMap<Integer, TempUser>();//HashMap생성

        int flag = 0; //반복데이터 여부

        for (Map<String, Object> stringObjectMap : param) {
            JSONObject jsonObject = new JSONObject(stringObjectMap);
            if (flag == 1) {
                int usercode = (int) stringObjectMap.get("userCode");
                int userCurrentTime = (int) stringObjectMap.get("userCurrentTime");

                String hopetime = stringObjectMap.get("hopetime").toString();
                String rep_str = hopetime.substring(1, hopetime.length() - 1).replace(" ", "");
                String[] split_str = rep_str.split(",");
                int[] hopetimes = new int[split_str.length];
                for (int i = 0; i < hopetimes.length; i++) {
                    hopetimes[i] = Integer.parseInt(split_str[i]);
                }

                userList.put(usercode, new TempUser(usercode, userCurrentTime, hopetimes));
            } else {
                flag = 1;
                date = (String) stringObjectMap.get("date");
                day = (String) stringObjectMap.get("day");
            }
        }

        //사용가능 데이터
        //date : 선택한 날짜
        //day : 선택한 요일
        //userList : 유저정보 (TempUser클래스)
        
        return param.size();
    }
}
