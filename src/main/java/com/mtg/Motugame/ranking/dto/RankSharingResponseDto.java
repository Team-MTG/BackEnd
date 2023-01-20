package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class RankSharingResponseDto {
    private Long myRank;
    private String nickname;
    private BigDecimal myProfit;
    private BigDecimal myYield;
    private List<OtherRankDto> otherRanking;
}
