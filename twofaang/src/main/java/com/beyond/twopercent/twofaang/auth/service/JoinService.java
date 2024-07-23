package com.beyond.twopercent.twofaang.auth.service;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.entity.enums.Role;
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

    public String join(JoinDTO joinDTO) {

        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String name = joinDTO.getName();
        String mobile = joinDTO.getMobile();
        Role role = Role.valueOf(joinDTO.getRole());

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {

            return "존재하는 회원 입니다.";
        }

        Member data = new Member();

        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setName(name);
        data.setGrade(gradeRepository.findByGradeName(GradeName.BRONZE));
        data.setMobile(mobile);
        data.setRole(role);

        userRepository.save(data);
        return name + "님 환영합니다!";
    }
}
