package sketcher.scheduling.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.*;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {

    //1. 인가 코드 받기
    //2. 토큰 받기
    //3. 사용자 로그인 처리

    public String getRefreshToken(String authorize_code) {
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "POST");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            setAuthorizationCode(sb, authorize_code);
            bw.write(sb.toString());
            bw.flush();
            bw.close();

            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            System.out.println("refresh_token : " + refresh_Token);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return refresh_Token;
    }

    public String refreshAccessToken(String refresh_Token) {
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "POST");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=refresh_token");
            sb.append("&client_id=e3dc4ec16faffa817d9ae7e059397b50"); // 본인이 발급받은 key
            sb.append("&refresh_token=" + refresh_Token);

            System.out.println(sb.toString());
            bw.write(sb.toString());
            bw.flush();
            bw.close();

            // 결과 코드가 200이라면 성공
            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            System.out.println("access_token : " + access_Token);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }


    public HashMap<String, Object> getUserInfo(String access_Token) {
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "POST");
            setRequestHeader(conn, access_Token);

            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // properties는 프로필 정보(닉네임/프로필 사진)를 갖고있습니다.(필수동의항목)
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            // kakao_account는 이메일, 성별, 연령대 등의 정보를 갖고있습니다.(선택동의항목)
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            // 카카오로그인시 사용자가 '이메일정보제공' 항목을 선택하지 않을 수도 있습니다.
            // 만약, 정보제공에 동의하지 않았다면 '"email_needs_agreement":true'값이 나오고,
            // 정보제공에 동의했다면 '"email_needs_agreement":false'값이 나옵니다.
            boolean email_needs_agreement = kakao_account.getAsJsonObject().get("email_needs_agreement").getAsBoolean();

            // 위에서 '"email_needs_agreement":true'값이 나온다면 kakao_account 객체 내에 "email" key값이
            // null이 되고맙니다.
            // 따라서 "email"값을 가져올 수 없게 되어 오류가 발생합니다.
            // 이를 방지하기 위해 if문으로 아래와 같이 처리했습니다.
            String email = "이메일 동의 항목에 사용자 동의 필요";
            if (email_needs_agreement == false) {
                email = kakao_account.getAsJsonObject().get("email").getAsString();
            }

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
            String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("profile_image", profile_image);
            userInfo.put("thumbnail_image", thumbnail_image);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 사용자 정보를 리턴합니다.
        return userInfo;
    }

    // 카카오계정 로그아웃
    public void kakaoLogout(String access_Token) {
        // 우선 session에 저장되어있는 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
        // 이후 로그아웃을 진행합니다.
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "GET");
            setRequestHeader(conn, access_Token);

            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 카카오계정 연결 해제
    public void kakaoUnlink(String access_Token) {
        // 우선 session에 저장되어있는 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
        // 이후 계정연결을 해제합니다.
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "GET");
            setRequestHeader(conn, access_Token);

            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 나에게 메시지 보내기
    public boolean isSendMessage(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "POST");
            conn.setDoOutput(true);
            setRequestHeader(conn, access_Token);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("template_object=" + addTemplateObjectJson());
            System.out.println(sb.toString());
            bw.write(sb.toString());
            bw.flush();
            bw.close();

            // 결과 코드가 200이라면 성공
            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            if (responseCode == 200) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 친구 목록 불러오기
    public HashMap<String, Object> getFriendsList(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/api/talk/friends"; // 친구 정보 요청
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> friendsList = new HashMap<>();
        HashMap<String, Object> friendsId = new HashMap<>();

        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "GET");
            conn.setDoOutput(true);
            setRequestHeader(conn, access_Token);

            // 결과 코드가 200이라면 성공
            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(result);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray friends = jsonObject.get("elements").getAsJsonArray();

            for (int i = 0; i < friends.size(); i++) {
                JsonObject element = friends.get(i).getAsJsonObject();
                String id = element.getAsJsonObject().get("id").getAsString();
                String uuid = element.getAsJsonObject().get("uuid").getAsString();
                String favorite = element.getAsJsonObject().get("favorite").getAsString();
                String profile_nickname = element.getAsJsonObject().get("profile_nickname").getAsString();
                String profile_thumbnail_image = element.getAsJsonObject().get("profile_thumbnail_image").getAsString();

                friendsList.put("id", id);
                friendsList.put("uuid", uuid);
                friendsList.put("favorite", favorite);
                friendsList.put("profile_nickname", profile_nickname);
                friendsList.put("profile_thumbnail_image", profile_thumbnail_image);

                friendsId.put(uuid, friendsList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return friendsId;
    }

    // 친구에게 메시지 보내기
    public boolean isSendMessageToFriends(String access_Token, HashMap<String, Object> friendsId) {
        String reqURL = "https://kapi.kakao.com/v1/api/talk/friends/message/default/send"; // 친구 정보 요청

        try {
            HttpURLConnection conn = getHttpURLConnection(reqURL, "POST");
            conn.setDoOutput(true);
            setRequestHeader(conn, access_Token);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            //json 형식으로 전송 데이터 셋팅
            Set uuid = friendsId.keySet();
            String json_uuids = new Gson().toJson(uuid);
            sb.append("receiver_uuids=" + json_uuids);
            sb.append("&template_object=" + addTemplateObjectJson());
            System.out.println(sb.toString());

            bw.write(sb.toString());
            bw.flush();
            bw.close();

            int responseCode = getResponseCode(conn);
            String result = getResponseBody(conn);

            if (responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public HttpURLConnection getHttpURLConnection(String reqURL, String method) throws IOException {
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);

        return conn;
    }

    public void setRequestHeader(HttpURLConnection conn, String access_Token) {
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
    }

    public void setAuthorizationCode(StringBuilder sb, String authorize_code) {
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=e3dc4ec16faffa817d9ae7e059397b50"); // 본인이 발급받은 key
        sb.append("&redirect_uri=http://localhost:8080/kakaoLogin"); // 본인이 설정해 놓은 경로
        sb.append("&code=" + authorize_code);
        sb.append("&scope=account_email,talk_message,friends");
    }

    public int getResponseCode(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        return responseCode;
    }

    public String getResponseBody(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        // response body : 0이면 성공
        System.out.println("response body : " + result);

        br.close();
        return result;
    }

    public JsonObject addTemplateObjectJson() {
        JsonObject json = new JsonObject();
        json.addProperty("object_type", "text");
        json.addProperty("text", "이번 주 근무스케줄이 배정되었어요!");
        json.addProperty("button_title", "근무스케줄 확인"); //button_title은 선택사항입니다.
        // 만약, button_title을 넣지 않으면 버튼명이 디폴트 값으로 "자세히 보기"로 나옵니다.
        JsonObject link = new JsonObject();
        link.addProperty("web_url", "http://sketcher-scheduling-service.ap-northeast-2.elasticbeanstalk.com/"); // 카카오개발자사이트 앱>앱설정>플랫폼>Web>사이트도메인에 등록한 도메인 입력
        link.addProperty("mobile_web_url", "http://sketcher-scheduling-service.ap-northeast-2.elasticbeanstalk.com/"); // 카카오개발자사이트 앱>앱설정>플랫폼>Web>사이트도메인에 등록한 도메인 입력
        //만약, 카카오개발자사이트에 도메인을 등록하지 않았다면 링크버튼 자체가 나오지 않습니다.
        json.add("link", link.getAsJsonObject());

        return json;
    }
}
