package com.beyond.twopercent.twofaang.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewModifyRequestDto {

/*
   DTO는 직접 작성하는 것 만들기

 */
        /*
        자동으로 들어가게 만들기
                 private Date reviewDate;
        private Date updateDate;
         */

        private int rating;
        private String reviewText;


}


