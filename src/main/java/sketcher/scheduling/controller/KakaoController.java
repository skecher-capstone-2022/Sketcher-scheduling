package sketcher.scheduling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sketcher.scheduling.service.KakaoService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    // 앱에서 로그아웃(정보제공 여부 유지)
    @RequestMapping("/kakaoLogout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String) session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("nickname");
        session.removeAttribute("result");
        session.removeAttribute("email");
        session.removeAttribute("profile_image");
        session.removeAttribute("thumbnail_image");

        return "logout";
    }

    // 앱과 연결된 카카오계정 연결해제(정보제공 여부도 전부 초기화)
    @RequestMapping("/kakaoUnlink")
    public String unlink(HttpSession session) {
        kakaoService.kakaoUnlink((String) session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("nickname");
        session.removeAttribute("result");
        session.removeAttribute("email");
        session.removeAttribute("profile_image");
        session.removeAttribute("thumbnail_image");

        return "logout";
    }

}
