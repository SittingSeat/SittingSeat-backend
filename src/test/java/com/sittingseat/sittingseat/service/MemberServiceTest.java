package com.sittingseat.sittingseat.service;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.dto.MemberJoinDto;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.enums.ProviderEnum;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.repository.FoodRepository;
import com.sittingseat.sittingseat.repository.MemberRepository;
import com.sittingseat.sittingseat.security.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    TestEntityManager em;

    @Test
    @Rollback(value = false)
    void join() {
        // given
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .name("lee")
                .email("lee@naver.com")
                .nickname("nicknameLee")
                .password("qwe123")
                .phone("010-1234-5678")
                .provider(ProviderEnum.EMAIL)
                .preferenceFoods(List.of())
                .dislikeFoods(List.of())
                .build();

        Member member = Member.toEntity(
                1L,
                memberJoinDto.getName(),
                memberJoinDto.getEmail(),
                memberJoinDto.getPassword(),
                memberJoinDto.getPhone(),
                memberJoinDto.getNickname(),
                memberJoinDto.getProvider()
        );

        Mockito.when(memberRepository.findByEmail(memberJoinDto.getEmail()))
                .thenReturn(Optional.of(member));


        // when
        memberService.join(memberJoinDto);
        Member findMember = memberRepository.findByEmail("lee@naver.com").orElse(null);

        // then
        assertThat(findMember).isNotNull();
        assertThat(findMember.getPhone()).isEqualTo("010-1234-5678");

    }


    @Test
    void login() {

    }
}