package com.mtg.Motugame.stock.controller;

import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.service.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockController {
    private final StockServiceImpl stockRepository;

    //Request로 받은 random값을 기준으로 주식을 골라 해당 주식의 한 달 종가를 반환
    @GetMapping("/stocks")
    public ResponseEntity<List<StockDataInfoDto>> findRandStockPriceInfo(@RequestParam("index") List<Integer> index) {
        List<StockDataInfoDto> stockDataInfos = stockRepository.getStocksPrices(index);
        return ResponseEntity.ok().body(stockDataInfos);

    }
}
