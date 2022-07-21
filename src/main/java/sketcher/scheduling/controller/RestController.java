package sketcher.scheduling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.repository.UserRepository;
import sketcher.scheduling.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {
    private final UserRepository userRepository;

    @GetMapping(value = "/find_All_Manager")
    public List<User> findAllManager(HttpServletRequest request, HttpServletResponse response) {
        return userRepository.findAllManager();
    }
}
