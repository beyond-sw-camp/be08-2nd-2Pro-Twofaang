package com.beyond.twopercent.twofaang.inquiry.repository;

import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Optional<Inquiry> findByInquiryIdAndMemberId(long inquiryId, long memberId);
}
