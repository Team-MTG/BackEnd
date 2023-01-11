package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ScoreInfo {
    private String stockCode;

    private BigDecimal profit;

    private BigDecimal yield;
}
