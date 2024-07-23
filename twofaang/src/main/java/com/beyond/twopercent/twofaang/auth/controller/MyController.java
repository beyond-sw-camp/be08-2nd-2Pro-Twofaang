package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MemberRepository memberRepository;

    @GetMapping("/my")
    public ResponseEntity<Member> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomMemberDetails) {
            return ResponseEntity.ok(memberRepository.findByEmail(((CustomMemberDetails) authentication.getPrincipal()).getEmail()));
        }
        throw new RuntimeException("User is not authenticated");
    }
}
