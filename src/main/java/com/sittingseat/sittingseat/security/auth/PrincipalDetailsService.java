package com.sittingseat.sittingseat.security.auth;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(username)
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.MEMBER_NOT_FOUND));

        return new PrincipalDetails(findMember);
    }
}
