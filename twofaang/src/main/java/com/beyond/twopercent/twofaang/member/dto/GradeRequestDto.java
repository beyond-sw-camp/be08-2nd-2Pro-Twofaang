package com.beyond.twopercent.twofaang.member.dto;

import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.Set;

@Getter
public class GradeRequestDto {

    private GradeName gradeName;

    private int targetAmount;

    private int discountRate;

}
