package com.beyond.twopercent.twofaang.inquiry.repository;

import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByInquiryId(long inquiryId);
}
