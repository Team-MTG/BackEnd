package com.mtg.Motugame.stock.controller;

import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.service.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockController {
    private final StockServiceImpl stockService;

    //Request로 받은 random값을 기준으로 주식을 골라 해당 주식의 한 달 종가를 반환
    @GetMapping("/stocks")
    public ResponseEntity<StockDataInfoDto> findRandStockPriceInfo(@RequestParam("seed") List<String> seeds) {
        StockDataInfoDto stockDataInfoDto = stockService.getStockDataInfoDto(seeds);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", Integer.toString(stockService.getStocksInfo()));
        headers.set("Access-Control-Expose-Headers", "X-Total-Count");
        return ResponseEntity.ok().headers(headers).body(stockDataInfoDto);
    }

    //몇개의 주식 데이터가 존재하는지 헤더에만 담아서 반환하는 메서드
    @RequestMapping(value = "/stocks", method = RequestMethod.HEAD)
    public ResponseEntity<Void> findHeadRandStockPriceInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", Integer.toString(stockService.getStocksInfo()));
        headers.set("Access-Control-Expose-Headers", "X-Total-Count");
        return ResponseEntity.ok().headers(headers).body(null);
    }
}
