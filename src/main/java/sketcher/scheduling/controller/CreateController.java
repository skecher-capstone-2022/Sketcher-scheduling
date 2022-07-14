package sketcher.scheduling.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.exception.NoAvailableManagerException;
import sketcher.scheduling.object.Day;
import sketcher.scheduling.object.TmpManager;
import sketcher.scheduling.object.TmpSchedule;
import sketcher.scheduling.service.ManagerAssignScheduleService;
import sketcher.scheduling.service.ManagerHopeTimeService;
import sketcher.scheduling.service.ScheduleService;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreateController<noAvailableManager> {

    final int DAY_TIME = 24;
    final int WEEK_SIZE = 7;
    Date date;
    Day day;
    int time;


    List<TmpSchedule> tmpSchedules = new ArrayList<>();
    List<TmpManager> tmpManagerList = new ArrayList<>();



//
//    @ResponseBody
//    @RequestMapping(value = "/select_cell")
//    public List<TmpManager> selectCell(@RequestParam("date") Date date, @RequestParam("cellId") String cellId) {
//        parsingCellId(cellId);
//        //정보에 해당하는 매니저 리스트 반환
//        return selectManagerListIncludedDateAndTime(date, time);
//    }

//    public List<TmpManager> selectManagerListIncludedDateAndTime(Date date, int time) {
//        for (TmpSchedule tmpSchedule : tmpSchedules) {
//            if (tmpSchedule.getDate() == date && tmpSchedule.getTime() == time) {
//                return tmpSchedule.getManagers();
//            }
//        }
//        return (List<TmpManager>) new NoAvailableManagerException();    //exception을 리스트 반환하는게 맞나...?
//    }

    private void parsingCellId(String cellId) {
        dayTypeChartoEnum(cellId.charAt(0));
        time = Integer.parseInt(cellId.substring(1, 3));
    }

    private void dayTypeChartoEnum(char day_typeChar) {
        if (day_typeChar == 'm') this.day = Day.MON;
        else if (day_typeChar == 't') this.day = Day.TUE;
        else if (day_typeChar == 'w') this.day = Day.WED;
        else if (day_typeChar == 'T') this.day = Day.THU;
        else if (day_typeChar == 'f') this.day = Day.FRI;
        else if (day_typeChar == 's') this.day = Day.SAT;
        else if (day_typeChar == 'S') this.day = Day.SUN;
    }


    public void createTmpSchedules(Date date) {
        for (int i = 1; i <= WEEK_SIZE; i++) {
            for (int time = 0; time < DAY_TIME; time++) {
//                switch (i % WEEK_SIZE) {
//                    case 0:
//                        tmpSchedules.add(new TmpSchedule(date, Day.MON, time));
//                        break;
//                    case 1:
//                        tmpSchedules.add(new TmpSchedule(date, Day.TUE, time));
//                        break;
//                    case 2:
//                        tmpSchedules.add(new TmpSchedule(date, Day.WED, time));
//                        break;
//                    case 3:
//                        tmpSchedules.add(new TmpSchedule(date, Day.THU, time));
//                        break;
//                    case 4:
//                        tmpSchedules.add(new TmpSchedule(date, Day.FRI, time));
//                        break;
//                    case 5:
//                        tmpSchedules.add(new TmpSchedule(date, Day.SAT, time));
//                        break;
//                    case 6:
//                        tmpSchedules.add(new TmpSchedule(date, Day.SUN, time));
//                        break;
//                }
//            }
            }

        }


    }

}