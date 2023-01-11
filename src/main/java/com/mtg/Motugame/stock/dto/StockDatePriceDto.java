package com.mtg.Motugame.stock.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class StockDatePriceDto {
    private LocalDate date;

    private BigDecimal price;
}
