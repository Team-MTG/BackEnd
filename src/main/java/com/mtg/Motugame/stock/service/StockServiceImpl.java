package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.entity.StockPriceEntity;
import com.mtg.Motugame.stock.dto.StockAverageDto;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.dto.StockPriceDto;
import com.mtg.Motugame.stock.dto.StockDatePriceDto;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockInfoRepository stockInfoRepository;

    @Override
    public StockDataInfoDto getStockDataInfoDto(List<String> seeds) {
        return StockDataInfoDto.builder()
                .stockPrices(getStocksPrices(seeds))
                .stockAverages(getAveragePrices(seeds))
                .build();
    }

    //주식가격정보를 가져옴
    private List<StockPriceDto> getStocksPrices(List<String> seeds) {
        List<StockPriceDto> stockPriceDtoList = new ArrayList<>();

        for (var seed : seeds) {
            //랜덤한 주식을 고름
            StockInfoEntity stockInfoEntity = getRandomStockInfoEntity(seed);
            //선택한 주식의 가격스리보 정보를 가져옴
            List<StockPriceEntity> stockPriceEntities = stockInfoEntity.getStockPriceEntities();

            //가져올 데이터의 시작 위치 인덱스번호 => 300*amount
            Integer amount = Integer.parseInt(seed.substring(3)) % 4;
            //해당 주식으로부터 300*amount에서 시작하여 300개의 데이터를 찾음
            List<StockPriceEntity> extractedList = stockPriceEntities.subList(300 * amount, 300 * amount + 300);

            stockPriceDtoList.add(StockPriceDto.builder()
                    .stockCode(stockInfoEntity.getStockCode())
                    .stockName(stockInfoEntity.getStockName())
                    .datas(getStockDatePriceDtoList(extractedList))
                    .build()
            );
        }
        return stockPriceDtoList;
    }

    //seed값을 기준으로 랜덤한 주식을 고르고 해당 주식의 랜덤시점으로부터의 300개의 데이터를 가져옴
    private StockInfoEntity getRandomStockInfoEntity(String seed) {
        //주식리스트에서 랜덤으로 접근할 시작 인덱스 번호로 주식코드 찾음
        Integer randStockIndex = Integer.parseInt(seed.substring(0, 3));

        //주식코드 번호로 정렬된 주식리스트를 가져옴
        List<StockInfoEntity> stockInfoEntityList = stockInfoRepository.findAllByOrderByStockCode();
        //랜덤한 주식을 고름
        return stockInfoEntityList.get(randStockIndex);
    }

    private List<StockDatePriceDto> getStockDatePriceDtoList(List<StockPriceEntity> extractedList) {
        List<StockDatePriceDto> stockDatePriceDtos = new ArrayList<>();

        //entity에서 필요한 정보(price, date) 추출 후 list저장
        for (StockPriceEntity entity : extractedList) {
            stockDatePriceDtos.add(StockDatePriceDto.builder()
                    .price(entity.getClose())
                    .date(entity.getDate()).build());
        }

        return stockDatePriceDtos;
    }

    private List<StockAverageDto> getAveragePrices(List<String> seeds) {
        List<StockAverageDto> stockAverageDtoList = new ArrayList<>();

        List<StockPriceDto> stockPriceDtoList = getStocksPrices(seeds);

        for (var stockPriceDto : stockPriceDtoList) {
            stockAverageDtoList.add(StockAverageDto.builder()
                    .stockName(stockPriceDto.getStockName())
                    .userAverageProfit(getUserAverageProfit(stockPriceDto.getStockCode()))
                    .build()
            );

        }
        return stockAverageDtoList;
    }

    //사용자들의 해당 주식 평균 수익률을 구함
    private Double getUserAverageProfit(String stockCode) {
        StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockCode(stockCode);

        BigDecimal totalProfit = new BigDecimal("0.0");//총 수익
        for (var scoreRecordEntity : stockInfoEntity.getScoreRecords()) {
            totalProfit = totalProfit.add(scoreRecordEntity.getProfit());
        }

        if (stockInfoEntity.getScoreRecords().size() == 0) {
            return 0.0;
        }

        return totalProfit.divide(new BigDecimal(String.valueOf(stockInfoEntity.getScoreRecords().size())),2).doubleValue();
    }
    
    public Integer getStocksInfo() {
        return stockInfoRepository.findAll().size();
    }
}
