package org.songbeom.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.songbeom.mallapi.domain.Member;
import org.songbeom.mallapi.domain.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.songbeom.mallapi.domain.MemberRole.*;

@SpringBootTest
@Log4j2
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //비밀번호 암호화 처리 객체 주입

    @Test
    void 회원가입() throws Exception {
        //given
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user"+i+"@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .nickname("사용자"+i)
                    .social(false)
                    .build();
            member.addRole(USER);

            if(i >= 5){
                member.addRole(MANAGER);
            }

            if(i >= 9){
                member.addRole(ADMIN);
            }

            memberRepository.save(member); //회원 저장
        }

        //then
        assertThat(memberRepository.count()).isEqualTo(10);

    }

    @Test
    void 유저조회() throws Exception {
        //given
        String email = "user0@naver.com";

        Member member = memberRepository.getWithRoles(email);
        //when

        log.info("---------------------------------");
        log.info(member);
        log.info(member.getMemberRoleList());

        //then
        assertThat(member).isNotNull();
        assertThat(member.getMemberRoleList().size()).isEqualTo(1);

    }




}