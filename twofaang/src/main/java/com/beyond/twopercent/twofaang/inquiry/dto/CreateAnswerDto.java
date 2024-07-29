package com.beyond.twopercent.twofaang.inquiry.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAnswerDto {

    @NotNull
    private long inquiryId;

    @NotBlank
    private String comment;
}
