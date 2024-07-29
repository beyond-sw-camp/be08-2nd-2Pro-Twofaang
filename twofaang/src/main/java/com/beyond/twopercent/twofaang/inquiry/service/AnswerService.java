package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;

import java.util.List;

public interface AnswerService {
	List<Answer> getAllAnswers();
//	List<AnswerDto> getAnswersByInquiryId(long inquiryId);
	void addAnswer(CreateAnswerDto createAnswerDto, long memberId);
	void updateAnswer(long answerId, UpdateAnswerDto updateAnswerDto, long memberId);
	void deleteAnswer(long answerId, long memberId);
}
