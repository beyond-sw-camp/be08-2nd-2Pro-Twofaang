package com.beyond.twopercent.twofaang.inquiry.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import com.beyond.twopercent.twofaang.inquiry.mapper.AnswerMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
	
	private final AnswerMapper answerMapper;

	@Override
	@Transactional
	public List<Answer> getAnswersByInquiryId(long inquiryId) {

		  return answerMapper.getAnswersByInquiryId(inquiryId);
	}

	@Override
	public Answer getAnswerById(long answerId) {
	
		return answerMapper.getAnswerById(answerId);
	}

	@Override
	public int addAnswer(Answer answer) {
		return answerMapper.addAnswer(answer);
	}

	@Override
	public int updateAnswer(Answer answer) {
	     return answerMapper.updateAnswer(answer);
	}

	@Override
	public int deleteAnswer(long answerId) {
		  return answerMapper.deleteAnswer(answerId);
	}

}
