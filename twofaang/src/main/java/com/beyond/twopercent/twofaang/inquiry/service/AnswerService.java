package com.beyond.twopercent.twofaang.inquiry.service;

import java.util.List;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;



public interface AnswerService {

	
	List<Answer> getAnswersByInquiryId(long inquiryId);

	Answer getAnswerById(long answerId);

	int addAnswer(Answer answer);

	int updateAnswer(Answer answer);

	int deleteAnswer(long answerId);

}
