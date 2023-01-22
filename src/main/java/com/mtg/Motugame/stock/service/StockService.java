package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.stock.dto.StockAverageDto;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.dto.StockPriceDto;

import java.util.List;

public interface StockService {
    StockDataInfoDto getStockDataInfoDto(List<String> seeds);
}
