package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.AnswerResponseDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDto> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(this::convertToAnswerResponseDTO)
                .collect(Collectors.toList());
    }

    private AnswerResponseDto convertToAnswerResponseDTO(Answer answer) {
        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setAnswerId(answer.getAnswerId());
        answerResponseDto.setInquiryId(answer.getInquiryId());
        answerResponseDto.setMemberEmail(answer.getMember().getEmail());
        answerResponseDto.setComment(answer.getComment());
        answerResponseDto.setResponseDate(answer.getResponseDate());
        answerResponseDto.setUpdateDate(answer.getUpdateDate());

        return answerResponseDto;
    }

    @Override
    @Transactional
    public void addAnswer(CreateAnswerDto createAnswerDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        if (isAdmin(email)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        Answer answer = Answer.builder()
                .inquiryId(createAnswerDto.getInquiryId())
                .member(member)
                .comment(createAnswerDto.getComment())
                .responseDate(LocalDateTime.now())
                .build();
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void updateAnswer(long answerId, UpdateAnswerDto updateAnswerDto, String email) {
        if (isAdmin(email)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("답변 번호를 찾을 수 없습니다."));

        if (!existingAnswer.getMember().getEmail().equals(email)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        existingAnswer.setComment(updateAnswerDto.getComment());
        existingAnswer.setUpdateDate(LocalDateTime.now());
        answerRepository.save(existingAnswer);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAdmin(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        return member.getRole() != Role.ROLE_ADMIN;
    }

    @Override
    @Transactional
    public void deleteAnswer(long answerId, String email) {
        Answer existingAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("답변 번호를 찾을 수 없습니다."));

        if (!existingAnswer.getMember().getEmail().equals(email)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        answerRepository.deleteById(answerId);
    }
}
