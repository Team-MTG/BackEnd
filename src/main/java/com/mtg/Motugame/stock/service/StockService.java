package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.stock.dto.StockAverageDto;
import com.mtg.Motugame.stock.dto.StockPriceDto;

import java.util.List;

public interface StockService {
    List<StockPriceDto> getStocksPrices(List<String> seeds);
    List<StockAverageDto> getAveragePrices(List<String> seeds);
}
