package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;

import java.util.List;

public interface InquiryService {

    List<Inquiry> getAllInquiries();

    Inquiry getInquiryById(long inquiryId);

    void addInquiry(CreateInquiryDto createInquiryDto);

    void updateInquiry(long inquiryId, UpdateInquiryDto updateInquiryDto, long memberId);

    void deleteInquiry(long inquiryId, long memberId, String password);

    boolean isOwner(long inquiryId, long memberId);

    boolean verifyPassword(long inquiryId, String password);
}
