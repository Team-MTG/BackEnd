package com.mtg.Motugame.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreInfo {
    private String stockCode;

    private BigDecimal profit;

    private BigDecimal yield;
}
