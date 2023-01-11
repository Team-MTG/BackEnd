package com.mtg.Motugame.stock.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StockDataInfoDto {
    private List<StockAverageDto> stockAverages;
    private List<StockPriceDto> stockPrices;
}
