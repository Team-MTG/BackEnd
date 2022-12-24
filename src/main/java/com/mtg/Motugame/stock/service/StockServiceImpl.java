package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.entity.StockPriceEntity;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.dto.StockDatePriceDto;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockInfoRepository stockInfoRepository;

    public List<StockDataInfoDto> getStocksPrices(List<Integer> index) {
        List<StockDataInfoDto> stockDataInfos = new ArrayList<>();

        List<StockInfoEntity> stockInfoEntitys = stockInfoRepository.findAll();
        for (Integer i : index) {


            //해당 주식코드의 entity를 찾음
            StockInfoEntity stockInfoEntity = stockInfoEntitys.get(i);

            //해당주식의 코드, 이름, 가격리스트 저장
            String stockCode = stockInfoEntity.getStockCode();
            String stockName = stockInfoEntity.getStockName();
            List<StockDatePriceDto> datas = getStockDatePriceDtoList(stockInfoEntity);

            //찾은 값을 저장
            stockDataInfos.add(StockDataInfoDto.builder()
                    .stockCode(stockCode)
                    .stockName(stockName)
                    .datas(datas).build()
            );
        }
        return stockDataInfos;
    }

    private List<StockDatePriceDto> getStockDatePriceDtoList(StockInfoEntity stockInfoEntity) {
        List<StockDatePriceDto> stockDatePriceDtos = new ArrayList<>();

        //주식 코드에 해당하는 가격정보 한달치 entity 리스트를 반환받음
        List<StockPriceEntity> stockPriceEntities = stockInfoEntity.getStockPriceEntities();

        //entity에서 필요한 정보(price, date) 추출 후 list저장
        for (StockPriceEntity entity : stockPriceEntities) {
            stockDatePriceDtos.add(StockDatePriceDto.builder()
                    .price(entity.getClose())
                    .date(entity.getDate()).build());
        }

        return stockDatePriceDtos;
    }
}
