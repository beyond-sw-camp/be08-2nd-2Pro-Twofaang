package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.InquiryResponseDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import com.beyond.twopercent.twofaang.inquiry.repository.InquiryRepository;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //모든 문의 조회
    @Override
    @Transactional(readOnly = true)
    public List<InquiryResponseDto> getAllInquiries() {
        return inquiryRepository.findAll().stream()
                .map(this::convertToInquiryResponseDto)
                .collect(Collectors.toList());
    }

    private InquiryResponseDto convertToInquiryResponseDto(Inquiry inquiry) { // Inquiry 객체를 InquiryResponseDto로 변환
        InquiryResponseDto inquiryResponseDtoDto = new InquiryResponseDto();
        inquiryResponseDtoDto.setInquiryId(inquiry.getInquiryId());
        inquiryResponseDtoDto.setMemberEmail(inquiry.getMember().getEmail());
        inquiryResponseDtoDto.setProductId(inquiry.getProduct().getProductId());
        inquiryResponseDtoDto.setInquiryTitle(inquiry.getInquiryTitle());
        inquiryResponseDtoDto.setInquiryText(inquiry.getInquiryText());
        inquiryResponseDtoDto.setInquiryType(inquiry.getInquiryType().name());
        inquiryResponseDtoDto.setInquiryDate(inquiry.getInquiryDate());
        return inquiryResponseDtoDto;
    }

    @Override
    @Transactional(readOnly = true) //읽기 전용
    public List<InquiryResponseDto> getInquiriesByMember(String email) {
        List<Inquiry> inquiries = inquiryRepository.findByMemberEmail(email);
        return inquiries.stream()
                .map(this::convertToInquiryResponseDto)
                .collect(Collectors.toList());
    }

    //문의작성
    @Override
    @Transactional
    public void addInquiry(CreateInquiryDto createInquiryDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다: " + email));

        Product product = productRepository.findById(createInquiryDto.getProductId())
                .orElseThrow(() -> new RuntimeException("상품 정보를 찾을 수 없습니다: " + createInquiryDto.getProductId()));

        Inquiry inquiry = Inquiry.builder()
                .member(member)
                .product(product)
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
    public void updateInquiry(long inquiryId, UpdateInquiryDto updateInquiryDto, String email) {
        Inquiry existingInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));

        if (!existingInquiry.getMember().getEmail().equals(email)) {
            throw new RuntimeException("이 문의를 수정할 권한이 없습니다");
        }

        existingInquiry.setInquiryTitle(updateInquiryDto.getTitle());
        existingInquiry.setInquiryText(updateInquiryDto.getContent());
        existingInquiry.setUpdateDate(LocalDateTime.now()); //업데이트한 날짜

        inquiryRepository.save(existingInquiry);
    }

    @Override
    @Transactional
    public void deleteInquiry(long inquiryId, String email, String password) {
        Inquiry existingInquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("해당 ID로 문의를 찾을 수 없습니다: " + inquiryId));

        if (!existingInquiry.getMember().getEmail().equals(email)) {
            throw new RuntimeException("이 문의를 삭제할 권한이 없습니다");
        }

        if (!existingInquiry.getInquiryPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        inquiryRepository.delete(existingInquiry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(long inquiryId, String email) {
        return inquiryRepository.findByInquiryIdAndMemberEmail(inquiryId, email).isPresent();
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
