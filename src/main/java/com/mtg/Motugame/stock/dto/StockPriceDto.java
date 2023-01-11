package com.mtg.Motugame.stock.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StockPriceDto {
    private String stockCode;

    private String stockName;

    private List<StockDatePriceDto> datas;
}
