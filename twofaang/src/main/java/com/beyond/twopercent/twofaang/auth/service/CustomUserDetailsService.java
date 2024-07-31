package com.beyond.twopercent.twofaang.auth.service;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (member.getStatus() == Status.N) {
            throw new UsernameNotFoundException("비활성화된 사용자입니다.");
        }

        return new CustomMemberDetails(member);
    }
}
