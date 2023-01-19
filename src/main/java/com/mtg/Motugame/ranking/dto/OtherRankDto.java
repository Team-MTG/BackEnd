package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OtherRankDto {
    private Long rank;
    private String nickname;
    private BigDecimal profit;
    private BigDecimal yield;
}
