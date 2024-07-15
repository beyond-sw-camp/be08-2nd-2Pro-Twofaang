package com.beyond.twopercent.twofaang.login.repository;

import com.beyond.twopercent.twofaang.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Integer> {

    Boolean existsByEmail(String email);

    Member findByEmail(String email);
}
