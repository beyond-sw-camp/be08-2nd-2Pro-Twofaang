package com.beyond.twopercent.twofaang.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAnswerDto {
        @NotBlank
        private String comment;
    }
