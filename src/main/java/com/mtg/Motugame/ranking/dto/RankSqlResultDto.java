package com.mtg.Motugame.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankSqlResultDto {
    Long id;

    Long userId;

    BigDecimal profit;

    BigDecimal totalYield;

    Integer num;
}
