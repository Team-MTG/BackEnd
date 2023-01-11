package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class RankRequestDto {
    private String nickname;

    private BigDecimal totalProfit;

    private BigDecimal totalYield;

    private List<ScoreInfo> scoreInfoList;
}
