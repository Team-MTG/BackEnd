package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.stock.dto.StockDataInfoDto;

import java.util.List;

public interface StockService {
    public List<StockDataInfoDto> getStocksPrices(int[] index);
}
