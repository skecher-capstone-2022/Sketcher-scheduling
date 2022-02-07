package sketcher.scheduling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goHome(HttpServletRequest request) {
        return "content/home";
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String goHome2(HttpServletRequest request) {
        return "content/home2";
    }
}

