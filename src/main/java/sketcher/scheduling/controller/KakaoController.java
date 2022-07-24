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

    @RequestMapping(value = "/kakaoLogin")
    public void kakaoReq(HttpServletResponse response, HttpSession session,
                            @RequestParam(value = "code", required = false) String code) throws Exception {
        if (code != null) {
            String access_Token = kakaoService.getAccessToken(code);
            HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
            boolean isSendMessage = kakaoService.isSendMessage(access_Token);
            HashMap<String, Object> friendsId = kakaoService.getFriendsList(access_Token);
//            boolean isSendMessageToFriends = kakaoService.isSendMessageToFriends(access_Token, friendsId);
            // 친구에게 메시지 보내기는 월 전송 제한이 있음 -> 주석 처리

            session.setAttribute("access_Token", access_Token);
//            model.addAttribute("isSendMessage", isSendMessage);

            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            if(isSendMessage) //  && isSendMessageToFriends
                out.println("alert('배정 알림을 완료하였습니다.')");
            else
                out.println("alert('배정 알림을 실패하였습니다.')");
            out.println("history.go(-1)");
            out.println("</script>");
            out.flush();
        }

//        return "calendar_admin";
    }

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
