package com.beyond.twopercent.twofaang.auth.service;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.common.entity.AuthCode;
import com.beyond.twopercent.twofaang.common.repository.AuthCodeRepository;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.entity.enums.Role;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.GradeRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GradeRepository gradeRepository;
    private final AuthCodeRepository authCodeRepository;

    public String join(JoinDTO joinDTO) throws Exception {
        String email = joinDTO.getEmail();
        String authCode = joinDTO.getAuthCode();

        // Check if the email already exists
        if (userRepository.existsByEmail(email)) {
            if (!userRepository.findByEmail(email).get().getStatus().equals(Status.N)) {
                throw new Exception("존재하는 회원입니다.");
            } else {
                userRepository.deleteById(userRepository.findByEmail(email).get().getMemberId());
            }
        }

        // Check if the auth code is correct
        AuthCode storedAuthCode = authCodeRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("인증번호가 없습니다."));

        if (!storedAuthCode.getAuthCode().equals(authCode)) {
            throw new Exception("인증번호가 다릅니다.");
        }

        // Proceed with the registration
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        member.setName(joinDTO.getName());
        member.setGrade(gradeRepository.findByGradeName(GradeName.BRONZE));
        member.setStatus(Status.Y);
        member.setMobile(joinDTO.getMobile());
        member.setRole(Role.ROLE_USER);

        userRepository.save(member);
        return member.getName() + "님 환영합니다!";
    }

}
