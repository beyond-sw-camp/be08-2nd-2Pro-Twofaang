package com.beyond.twopercent.twofaang.auth.service;

import com.beyond.twopercent.twofaang.auth.dto.JoinDTO;
import com.beyond.twopercent.twofaang.auth.repository.UserRepository;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String joinProcess(JoinDTO joinDTO) {

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
        data.setMobile(mobile);
        data.setRole(role);

        userRepository.save(data);
        return name + "님 환영합니다!";
    }
}
