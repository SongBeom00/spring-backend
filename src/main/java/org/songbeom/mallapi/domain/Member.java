package org.songbeom.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString(exclude = "memberRoleList")
public class Member {

    @Id
    private String email;


    private String password;

    private String nickname;

    private boolean social;


    @Builder.Default
    @Enumerated(STRING) //EnumType.ORDINAL 은 순서로 저장 EnumType.STRING 은 문자열로 저장
    @ElementCollection(fetch = LAZY)
    private List<MemberRole> memberRoleList = new ArrayList<>();

    public void addRole(MemberRole memberRole) {
        memberRoleList.add(memberRole);
    }

    public void clearRole(){
        memberRoleList.clear();
    }


}
