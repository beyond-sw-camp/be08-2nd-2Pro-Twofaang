package com.beyond.twopercent.twofaang.auth.service;


import com.beyond.twopercent.twofaang.auth.dto.oAuth2.*;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.entity.enums.Role;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.GradeRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2Response response = null;
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if (clientName.equals("naver")) {
            response = new NaverResponse(attributes);
        } else if (clientName.equals("google")) {
            response = new GoogleResponse(attributes);
        } else {
            return null;
        }

        String email = response.getEmail();
        Optional<Member> isExist = memberRepository.findByEmail(email);

        if (isExist.isPresent() && isExist.get().getStatus() == Status.N) {
            memberRepository.deleteById(isExist.get().getMemberId());
        }

        saveOrUpdateUser(response, email);

        OAuth2MemberDto oAuth2MemberDto = OAuth2MemberDto.builder()
                .name(response.getName())
                .email(email)
                .role("ROLE_USER")
                .build();

        return new CustomOAuth2Member(oAuth2MemberDto);
    }

    private void saveOrUpdateUser(OAuth2Response response, String email) {
        Optional<Member> isExist = memberRepository.findByEmail(email);

        if (isExist.isPresent()) {
            Member member = isExist.get();
            member.setName(response.getName());
            member.setEmail(response.getEmail());
            memberRepository.save(member);
        } else {
            Member member = new Member();
            member.setEmail(email);
            member.setName(response.getName());
            member.setRole(Role.ROLE_USER); // default role
            member.setGrade(gradeRepository.findByGradeName(GradeName.BRONZE));
            member.setPassword("OAuth2 인증 사용자");
            memberRepository.save(member);
        }
    }
}

