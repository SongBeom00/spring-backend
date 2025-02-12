package org.songbeom.mallapi.dto;


import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; //상속받으면 생성자가 필요합니다.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MemberDTO extends User {

    private String email, password, nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();


    public MemberDTO(String email, String password, String nickname, boolean social, List<String> roleNames) {
        super(
                email,
                password,
                roleNames
                        .stream()
                        .map(roleName ->new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList()));

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }



    public Map<String,Object> getClaims(){ //JWT 에 담을 데이터 민감한 정보인 password 는 제외하고 담는다.
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("email",email);
        dataMap.put("password",password);
        dataMap.put("nickname",nickname);
        dataMap.put("social",social);
        dataMap.put("roleNames",roleNames);

        return dataMap;

    }





}