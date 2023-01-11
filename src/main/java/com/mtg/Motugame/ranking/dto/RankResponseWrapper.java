package com.mtg.Motugame.ranking.dto;

import java.math.BigDecimal;

public interface RankResponseWrapper {
    Long getId();
    Long getUserId();
    BigDecimal getProfit();
    BigDecimal getTotalYield();
    Integer getNum();

}
