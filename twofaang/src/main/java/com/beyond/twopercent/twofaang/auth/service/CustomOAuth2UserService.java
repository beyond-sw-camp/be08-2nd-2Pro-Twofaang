package com.beyond.twopercent.twofaang.auth.service;


import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.dto.oAuth2.*;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.Role;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest -> registration 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2Response response = null;
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 존재하는 provider 인지 확인
        if (clientName.equals("naver")) {
            response = new NaverResponse(attributes);
        } else if (clientName.equals("google")) {
            response = new GoogleResponse(attributes);
        }else {
            return null;
        }

        // provider name + provider Id 로 username(식별자) 생성
        String email = response.getEmail();
        CustomOAuth2Member customOAuth2Member = null;
        String role = "ROLE_USER";
        System.out.println(response);
        // DB save
        saveUser(response, email, role);

        // Entity 목적 순수하게 유지하기 위해서 dto 로 전달..
        OAuth2MemberDto oAuth2MemberDto = OAuth2MemberDto.builder()
                .name(response.getName())
                .email(email)
                .role(role)
                .build();

        customOAuth2Member = new CustomOAuth2Member(oAuth2MemberDto);

        // 서버 내부에서 사용하기 위한 인증 정보
        return customOAuth2Member;
    }

    /**
     * 이미 존재하는 경우 update
     * 존재하지 않는 경우 save
     */
    private void saveUser(OAuth2Response response, String email, String role) {
        // DB 조회
        Member isExist = memberRepository.findByEmail(email);

        if (isExist != null) {
            isExist.setName(response.getName());
            isExist.setEmail(response.getEmail());
            isExist.setRole(Role.valueOf(role));
        } else {
            Member member = new Member();

            member.setEmail(email);
            member.setRole(Role.valueOf(role));
            member.setName(response.getName());
            member.setPassword("OAuth2 인증 사용자");
            member.setEmail(response.getEmail());

            memberRepository.save(member);
        }
    }
}
