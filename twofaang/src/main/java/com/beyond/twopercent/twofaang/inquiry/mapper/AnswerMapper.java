package com.beyond.twopercent.twofaang.inquiry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.beyond.twopercent.twofaang.inquiry.entity.Answer;

@Mapper
public interface AnswerMapper {

    List<Answer> getAnswersByInquiryId(@Param("inquiryId") long inquiryId);
    Answer getAnswerById(@Param("answerId") long answerId);
    int addAnswer(Answer answer);
    int updateAnswer(Answer answer);
    int deleteAnswer(@Param("answerId") long answerId);
}