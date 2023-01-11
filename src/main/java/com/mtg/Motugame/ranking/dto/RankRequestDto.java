package com.mtg.Motugame.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankRequestDto {
    private String nickname;

    private BigDecimal totalProfit;

    private BigDecimal totalYield;

    private List<ScoreInfo> scoreInfoList;
}
