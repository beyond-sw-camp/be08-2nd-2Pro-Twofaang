package com.beyond.twopercent.twofaang.inquiry.service;

import com.beyond.twopercent.twofaang.inquiry.dto.AnswerResponseDto;
import com.beyond.twopercent.twofaang.inquiry.dto.CreateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateAnswerDto;

import java.util.List;

public interface AnswerService {
	List<AnswerResponseDto> getAllAnswers(); //DTO 데이터

	void addAnswer(CreateAnswerDto createAnswerDto, String email);

	void updateAnswer(long answerId, UpdateAnswerDto updateAnswerDto, String email);

	void deleteAnswer(long answerId, String email);

	boolean isAdmin(String email);
}
