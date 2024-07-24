package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.entity.InquiryEntity;
import com.beyond.twopercent.twofaang.inquiry.mapper.InquiryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryMapper inquiryMapper;

    @Override
    @Transactional()
    public List<InquiryEntity> getAllInquiries() {
        return inquiryMapper.getAllInquiries();
    }

    @Override
    @Transactional()
    public InquiryEntity getInquiryById(long inquiryId) {
        return inquiryMapper.getInquiryById(inquiryId);
    }

    @Override
    @Transactional
    public int addInquiry(InquiryEntity inquiry) {
        return inquiryMapper.addInquiry(inquiry);
    }

    @Override
    @Transactional
    public int updateInquiry(InquiryEntity inquiry) {
        return inquiryMapper.updateInquiry(inquiry);
    }

    @Override
    @Transactional
    public int deleteInquiry(long inquiryId) {
        return inquiryMapper.deleteInquiry(inquiryId);
    }
}

