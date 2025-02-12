package org.songbeom.mallapi.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;

public class JWTUtil {


    private static final String SECRET_KEY = "MySecretKeyForJWTMySecretKeyForJWT";


    public static String generateToken(Map<String,Object> valueMap, int expirationMinutes){
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JWTUtil.SECRET_KEY.getBytes(UTF_8));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String jwtStr = Jwts.builder()
                .setHeader(Map.of("typ","jwt"))
                .addClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant()))
                .signWith(key)
                .compact();

        return jwtStr;
    }

    public static Map<String,Object> validateToken(String token){

        Map<String,Object> claim = null;


        try {

            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.SECRET_KEY.getBytes(UTF_8));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)// 파싱 및 검증, 실패시 에러 발생
                    .getBody();

        }catch (MalformedJwtException malformedJwtException){
            throw new CustomJWTException("Malformed JWT"); //JWT 가 올바르지 않을 때
        }
        catch (ExpiredJwtException expiredJwtException){
            throw new CustomJWTException("Expired JWT"); //JWT 가 만료되었을 때
        }
        catch (InvalidClaimException invalidClaimException){
            throw new CustomJWTException("Invalid Claim"); //JWT 의 Claim 이 올바르지 않을 때
        }
        catch (JwtException jwtException){
            throw new CustomJWTException("JWT Error"); //그 외의 에러
        }
        catch (Exception e) {
            throw new CustomJWTException("Error");
        }

        return claim;
    }



}
