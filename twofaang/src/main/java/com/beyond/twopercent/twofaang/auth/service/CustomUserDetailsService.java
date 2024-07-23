package com.beyond.twopercent.twofaang.auth.service;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.member.entity.Member;
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

        Member userData = userRepository.findByEmail(email);

        if (userData != null) {

            return new CustomMemberDetails(userData);
        }
        return null;
    }
}
