package com.beyond.twopercent.twofaang.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {

/*
   DTO는 직접 작성하는 것 만들기

 */
        /*
        자동으로 들어가게 만들기
                 private Date reviewDate;
        private Date updateDate;
         */

        private long memberId;
        private long productId;
        private int rating;
        private String reviewText;


}


