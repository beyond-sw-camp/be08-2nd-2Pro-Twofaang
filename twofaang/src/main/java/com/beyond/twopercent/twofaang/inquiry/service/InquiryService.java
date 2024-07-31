package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.InquiryResponseDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;

import java.util.List;

public interface InquiryService {
    List<InquiryResponseDto> getAllInquiries();

    List<InquiryResponseDto> getInquiriesByMember(String email);

    void addInquiry(CreateInquiryDto createInquiryDto, String email);

    void updateInquiry(long inquiryId, UpdateInquiryDto updateInquiryDto, String email);

    void deleteInquiry(long inquiryId, String email, String password);

    boolean isOwner(long inquiryId, String email);

    boolean verifyPassword(long inquiryId, String password);
}
