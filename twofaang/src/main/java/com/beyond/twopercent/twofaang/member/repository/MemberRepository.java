package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Boolean existsByEmail(String email);
}
