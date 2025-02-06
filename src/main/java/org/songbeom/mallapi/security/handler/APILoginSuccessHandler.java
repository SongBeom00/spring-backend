package org.songbeom.mallapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.dto.MemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler { //로그인 성공시 처리할 핸들러


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("=================================== login success ====================================");
        log.info(authentication);
        log.info("=================================== login success ====================================");

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal(); //로그인한 사용자의 정보를 가져온다.

        Map<String, Object> claims = memberDTO.getClaims();
        
        claims.put("accessToken", ""); //accessToken 을 claims 에 저장한다.
        claims.put("refreshToken", ""); //refreshToken 을 claims 에 저장한다.

        Gson gson = new Gson();

        String temp = gson.toJson(claims);//claims 를 json 으로 변환한다.

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        PrintWriter printWriter = response.getWriter();
        printWriter.println(temp); //json 을 응답한다.
        printWriter.close();

    }







}
