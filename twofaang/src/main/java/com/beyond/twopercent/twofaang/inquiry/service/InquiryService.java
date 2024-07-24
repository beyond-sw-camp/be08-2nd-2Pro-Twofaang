package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import com.beyond.twopercent.twofaang.inquiry.entity.InquiryEntity;

import java.util.List;

public interface InquiryService {
    List<InquiryEntity> getAllInquiries();

    InquiryEntity getInquiryById(long inquiryId);

    int addInquiry(InquiryEntity inquiry);

    int updateInquiry(InquiryEntity inquiry);

    int deleteInquiry(long inquiryId);
}
