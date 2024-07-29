package com.beyond.twopercent.twofaang.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateInquiryDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
