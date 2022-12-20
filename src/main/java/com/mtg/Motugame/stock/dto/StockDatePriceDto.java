package com.mtg.Motugame.stock.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StockDatePriceDto {
    private LocalDate date;
    private Integer price;
}
