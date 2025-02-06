package org.songbeom.mallapi.config;

import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.controller.formatter.LocalDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig  implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {

        log.info("addFormatters..........................");
        registry.addFormatter(new LocalDateFormatter());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") //모든 경로 cors 설정
//                .allowedOrigins("*") //어디에서 들어오는 연결을 허락해줄까
//                .maxAge(500)
//                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS"); //메소드 허용
//    }






}
