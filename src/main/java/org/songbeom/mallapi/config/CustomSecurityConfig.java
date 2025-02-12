package org.songbeom.mallapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.security.filter.JWTCheckFilter;
import org.songbeom.mallapi.security.handler.APILoginFailHandler;
import org.songbeom.mallapi.security.handler.APILoginSuccessHandler;
import org.songbeom.mallapi.security.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("----------------------security config----------------------");

        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()); //cors 설정
        });

        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }); //세션 사용 안함

        http.csrf(AbstractHttpConfigurer::disable); // csrf 사용 안함

        http.formLogin(config -> {
            config.loginPage("/api/member/login"); //로그인 페이지 설정 (기본값은 /login) -> api/member/login 으로 설정
            config.successHandler(new APILoginSuccessHandler()); //로그인 성공 핸들러
            config.failureHandler(new APILoginFailHandler()); //로그인 실패 핸들러
        });

        http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class); //JWTCheckFilter 를 추가 한다.

        http.exceptionHandling(config -> {
            config.accessDeniedHandler(new CustomAccessDeniedHandler()); //접근 거부 핸들러
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호 암호화 bcrypt 사용
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() { //cors 설정 security 에서 사용할 수 있도록 설정

        CorsConfiguration configuration = new CorsConfiguration();

        // 모든 도메인 허용 (개발용), 프로덕션에서는 특정 도메인 설정
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // cors 설정을 적용할 url 설정
        source.registerCorsConfiguration("/**", configuration); //모든 경로에 대해 cors 설정

        return source;
    }



}
