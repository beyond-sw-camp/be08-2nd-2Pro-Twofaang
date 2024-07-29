package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import com.beyond.twopercent.twofaang.inquiry.repository.AnswerRepository;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Role;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Answer> getAllAnswers() {

        return answerRepository.findAll();

//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<AnswerDto> getAnswersByInquiryId(long inquiryId) {
//        return answerRepository.findByInquiryId(inquiryId)
//                .stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public void addAnswer(CreateAnswerDto createAnswerDto, long memberId) {
        if (!isAdmin(memberId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        Answer answer = Answer.builder()
                .inquiryId(createAnswerDto.getInquiryId())
                .memberId(memberId)
                .comment(createAnswerDto.getComment())
                .responseDate(LocalDateTime.now())
                .build();
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void updateAnswer(long answerId, UpdateAnswerDto updateAnswerDto, long memberId) {
        if (!isAdmin(memberId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        answerRepository.findById(answerId).ifPresent(existingAnswer -> {
            existingAnswer.setComment(updateAnswerDto.getComment());
            existingAnswer.setUpdateDate(LocalDateTime.now());
            answerRepository.save(existingAnswer);
        });
    }

    @Override
    @Transactional
    public void deleteAnswer(long answerId, long memberId) {
        if (!isAdmin(memberId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        answerRepository.deleteById(answerId);
    }

    @Transactional(readOnly = true)
    public boolean isAdmin(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return member.getRole() == Role.ROLE_ADMIN;
    }

//    private AnswerDto convertToDto(Answer answer) {
//        return AnswerDto.builder()
//                .answerId(answer.getAnswerId())
//                .inquiryId(answer.getInquiryId())
//                .memberId(answer.getMemberId())
//                .comment(answer.getComment())
//                .responseDate(answer.getResponseDate())
//                .updateDate(answer.getUpdateDate())
//                .build();
//    }
}
