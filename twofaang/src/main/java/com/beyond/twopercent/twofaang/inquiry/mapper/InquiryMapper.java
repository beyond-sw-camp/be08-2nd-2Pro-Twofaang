package com.beyond.twopercent.twofaang.inquiry.mapper;

import com.beyond.twopercent.twofaang.inquiry.entity.InquiryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface InquiryMapper {
    List<InquiryEntity> getAllInquiries();

    InquiryEntity getInquiryById(@Param("inquiryId") long inquiryId);

    int addInquiry(InquiryEntity inquiry);

    int updateInquiry(InquiryEntity inquiry);

    int deleteInquiry(@Param("inquiryId") long inquiryId);
}
