package com.beyond.twopercent.twofaang.common.repository;

import com.beyond.twopercent.twofaang.common.entity.AuthCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {

    void deleteByEmail(String email);

    @Transactional
    boolean existsByEmail(String email);

    Optional<AuthCode> findByEmail(String email);

}
