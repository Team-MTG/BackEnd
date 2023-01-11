package com.mtg.Motugame.stock.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockAverageDto {
    private String stockName;
    private Double userAverageProfit;
}
