package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import com.beyond.twopercent.twofaang.inquiry.repository.AnswerRepository;
import com.beyond.twopercent.twofaang.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Inquiry getInquiryById(long inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));
    }

    @Override
    @Transactional
    public void addInquiry(CreateInquiryDto createInquiryDto) {
        Inquiry inquiry = Inquiry.builder()
                .memberId(createInquiryDto.getMemberId())
                .productId(createInquiryDto.getProductId())
                .inquiryTitle(createInquiryDto.getInquiryTitle())
                .inquiryText(createInquiryDto.getInquiryText())
                .inquiryType(createInquiryDto.getInquiryType())
                .inquiryDate(LocalDateTime.now())
                .inquiryPassword(createInquiryDto.getInquiryPassword())
                .build();
        inquiryRepository.save(inquiry);
    }

    @Override
    @Transactional
    public void updateInquiry(long inquiryId, UpdateInquiryDto updateInquiryDto, long memberId) {
        Inquiry existingInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));

        if (existingInquiry.getMemberId() != memberId) {
            throw new RuntimeException("이 문의를 업데이트할 권한이 없습니다");
        }

        existingInquiry.setInquiryTitle(updateInquiryDto.getTitle());
        existingInquiry.setInquiryText(updateInquiryDto.getContent());
        existingInquiry.setInquiryDate(LocalDateTime.now());

        inquiryRepository.save(existingInquiry);
    }

    @Override
    @Transactional
    public void deleteInquiry(long inquiryId, long memberId, String password) {
        Inquiry existingInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));

        if (existingInquiry.getMemberId() != memberId) {
            throw new RuntimeException("이 문의를 삭제할 권한이 없습니다");
        }

        if (!existingInquiry.getInquiryPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        inquiryRepository.delete(existingInquiry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(long inquiryId, long memberId) {
        return inquiryRepository.findByInquiryIdAndMemberId(inquiryId, memberId).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyPassword(long inquiryId, String password) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));
        if (!inquiry.getInquiryPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return true;
    }
}
