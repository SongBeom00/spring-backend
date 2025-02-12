package org.songbeom.mallapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.util.CustomJWTException;
import org.songbeom.mallapi.util.JWTUtil;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {


    @RequestMapping("/api/member/refresh")
    public Map<String,Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            String refreshToken
    )
    {
        log.info("=================================REFRESH=================================");
        if(refreshToken == null){
            throw  new CustomJWTException("NULL REFRESH TOKEN");
        }

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new CustomJWTException("INVALID ACCESS TOKEN");
        }

        // Bearer // 7 글자 JWT 문자열 -> Bearer 를 제외한 문자열을 가져온다.
        String accessToken = authHeader.substring(7);

        //AccessToken의 만료여부 확인 -> 만료되지 않았다면 그대로 반환
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        //RefreshToken 검증

        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);

        String newAccessToken = JWTUtil.generateToken(claims, 10);

        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);

    }


    // 시간이 1시간 미만으로 남았는지 확인
    private boolean checkTime(Integer exp){

        // JWT exp를 날짜로 변환
        Date expDate = new Date((long) exp * 1000);

        // 현재 시간과의 차이 계산 -> 밀리초로 나옴
        long gap = expDate.getTime() - System.currentTimeMillis();

        // 분으로 변환
        long leftMin = gap / (1000 * 60);


        // 60분 미만이면 true 반환
        return leftMin < 60;

    }

    private boolean checkExpiredToken(String token){
        try{
            JWTUtil.validateToken(token);
        }catch (CustomJWTException ex){
            if (ex.getMessage().equals("EXPIRED TOKEN")){
                return true;
            }
            return true;
        }
        return false;
    }



}
