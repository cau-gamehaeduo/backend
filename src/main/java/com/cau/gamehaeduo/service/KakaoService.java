package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;

import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.*;

@Log4j2
@Service
public class KakaoService {

    public long checkKakaoUser(String token) throws BaseException{
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> resMap = null;
        ObjectMapper mapper = new ObjectMapper();

        //kakao_access_token 으로 사용자 유효한지 확인
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            if(responseCode != 200){
                log.error("만료되거나 유효하지 않은 KAKAO ACCESS TOKEN 입니다.");
                throw new BaseException(INVALID_ACCESS_KAKAO);
            }


//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuffer stringBuffer = new StringBuffer();
//            String inputLine;
//
//            while ((inputLine = bufferedReader.readLine()) != null)  {
//                stringBuffer.append(inputLine);
//            }
//            bufferedReader.close();


            InputStream inputStream = conn.getInputStream();
            resMap = mapper.readValue(inputStream, Map.class);	//결과 받아옴.




//            int kakaoIdx = Integer.parseInt(String.valueOf(jsonObject.get("id")));
            try{
                long kakaoIdx = Long.parseLong(String.valueOf(resMap.get("id")));
                return kakaoIdx;
            }catch (NumberFormatException e){
                long aa= Long.parseLong("2516106831");

                log.error(resMap.get("id"));
                log.error(e);

            }


        } catch (MalformedURLException |ProtocolException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
        return 0;
    }


}

    /**
     *
     *     public static void insertLog(HttpServletRequest request, String memberId, String publicDataPk) {
     *         String url = "http://localhost:8080/logs";
     *
     *         AsyncRestTemplate restTemplate = new AsyncRestTemplate(); // 비동기 전달
     *         HttpHeaders httpHeaders = new HttpHeaders();
     *         httpHeaders.setContentType(MediaType.APPLICATION_JSON);
     *
     *         JSONObject jsonObject = new JSONObject();
     *
     *         jsonObject.put("memberId", memberId);
     *         jsonObject.put("sessionId", request.getRequestedSessionId());
     *         jsonObject.put("publicDataId", publicDataId);
     *
     *         HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
     *         restTemplate.postForEntity(url, logRequest, String.class);
     *
     *         ... 생략
     *     }
     */
//    public static void insertLog(HttpServletRequest request, String memberId, String publicDataPk) {
//        String url = "http://localhost:8080/logs";
//
//        AsyncRestTemplate restTemplate = new AsyncRestTemplate(); // 비동기 전달
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        JSONObject jsonObject = new JSONObject();
//
//        jsonObject.put("memberId", memberId);
//        jsonObject.put("sessionId", request.getRequestedSessionId());
//        jsonObject.put("publicDataId", publicDataId);
//
//        HttpEntity<String> logRequest = new HttpEntity<>(jsonObject.toString(), httpHeaders);
//        restTemplate.postForEntity(url, logRequest, String.class);
//
//        ... 생략
//    }
