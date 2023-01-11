package com.mtg.Motugame.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class RankResponseDto {
    private Long rank;

    private String nickname;

    private BigDecimal profit;

    private BigDecimal yield;

}
