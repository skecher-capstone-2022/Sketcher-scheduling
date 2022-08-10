package sketcher.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sketcher.scheduling.domain.EstimatedNumOfCardsPerHour;
import sketcher.scheduling.domain.PercentageOfManagerWeights;
import sketcher.scheduling.dto.PercentageOfManagerWeightsDto;
import sketcher.scheduling.repository.EstimatedNumOfCardsPerHourRepository;
import sketcher.scheduling.repository.PercentageOfManagerWeightsRepository;
import sketcher.scheduling.service.PercentageOfManagerWeightsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    EstimatedNumOfCardsPerHourRepository estimatedNumOfCardsRepository;

    @Autowired
    PercentageOfManagerWeightsRepository percentageOfManagerWeightsRepository;

    @Autowired
    PercentageOfManagerWeightsService percentageOfManagerWeightsService;

    @RequestMapping(value = "/config_create_schedule")
    public String configCreateSchedule(Model model) {

        PercentageOfManagerWeights percentageOfManagerWeights = percentageOfManagerWeightsRepository.findAll().get(0);

        PercentageOfManagerWeightsDto percentageOfManagerWeightsDto = PercentageOfManagerWeightsDto.builder()
                .id(percentageOfManagerWeights.getId())
                .high(percentageOfManagerWeights.getHigh())
                .middle(percentageOfManagerWeights.getMiddle())
                .low(percentageOfManagerWeights.getLow())
                .build();

        model.addAttribute("percent", percentageOfManagerWeightsDto);

        return "full-calendar/calendar_create_config";
    }

    @RequestMapping(value = "/updatePercent", method = RequestMethod.POST)
    public String updatePercent(@RequestParam String id,
                                @RequestParam String high,
                                @RequestParam String middle,
                                @RequestParam String low) {

        PercentageOfManagerWeightsDto percentageOfManagerWeightsDto = PercentageOfManagerWeightsDto.builder()
                .id(Integer.parseInt(id))
                .high(Integer.parseInt(high))
                .middle(Integer.parseInt(middle))
                .low(Integer.parseInt(low))
                .build();

        percentageOfManagerWeightsService.updatePercentageOfManagerWeights(percentageOfManagerWeightsDto);

        return "redirect:/config_create_schedule";
    }


    @GetMapping("/set_est_num_of_cards")
    @ResponseBody
    public List<EstimatedNumOfCardsPerHour> setEstNumOfCards() {
        return estimatedNumOfCardsRepository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        return "full-calendar/calendar";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String calendar(HttpServletRequest request) {
        //사용자 권한에 따라 admin이면 /calendar_damin으로 이동할 수 있도록 조건문 추가
        return "full-calendar/calendar";
    }

    @RequestMapping(value = "/calendar_admin", method = RequestMethod.GET)
    public String calendar_admin(HttpServletRequest request) {
        return "full-calendar/calendar-admin";
    }

    @RequestMapping(value = "/calendar_admin_update", method = RequestMethod.GET)
    public String calendar_admin_update(HttpServletRequest request) {
        return "full-calendar/calendar-admin-update";
    }

    @GetMapping(value = "/create_schedule")
    public String createSchedule() {
        return "full-calendar/calendar_create";
    }


}