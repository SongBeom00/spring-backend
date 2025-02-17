package org.songbeom.mallapi.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.dto.MemberDTO;
import org.songbeom.mallapi.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {


    @Override // 사용자가 로그인을 하면 필터를 토큰이 없어서 필터를 타지 않는다.
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException { // 필터를 적용 할지 말지 결정 하는 메소드
        log.info("=================================JWTCheckFilter=================================");
        // true == not checking

        String path = request.getRequestURI();

        log.info("path: {} ====================================",path);

        if (path.startsWith("/api/member/")){
            return true;
        }

        // false == check
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=================================JWTCheckFilter=================================");

        String authHeaderStr = request.getHeader("Authorization");

        try{
            // Bearer // 7 글자 JWT 문자열 -> Bearer 를 제외한 문자열을 가져온다.
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims =  JWTUtil.validateToken(accessToken);

            log.info("claims: {}",claims);



            // destination url 성공 하면 다음 필터로 넘어간다.
            //filterChain.doFilter(request,response); // 다음 필터로 넘어 간다.
            String email = (String) claims.get("email");
            String password = (String) claims.get("password");
            String nickname =  (String) claims.get("nickname");
            Boolean social = (Boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(email,password,nickname,social,roleNames);

            log.info("=========================== memberDTO ===========================");
            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO,null,memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request,response); // 다음 필터로 넘어 간다.

        }catch (Exception e){
            log.error(e.getMessage());
            Gson g =new Gson();
            String temp = g.toJson(Map.of("error","ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter printWriter = response.getWriter();
            printWriter.println(temp);
            printWriter.close();
        }





    }
}
