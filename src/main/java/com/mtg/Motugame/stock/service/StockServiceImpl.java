package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.entity.ScoreRecordEntity;
import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.entity.StockPriceEntity;
import com.mtg.Motugame.stock.dto.StockAverageDto;
import com.mtg.Motugame.stock.dto.StockPriceDto;
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

    //주식가격정보를 가져옴
    public List<StockPriceDto> getStocksPrices(List<String> seeds) {
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
        Integer randStockIndex = Integer.parseInt(seed.substring(0, 2));

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

    @Override
    public List<StockAverageDto> getAveragePrices(List<String> seeds) {
//        List<StockInfoEntity> stocks = getStockInfoEntitiesUsingIndex(convertSeedsToIndex(seeds));
//
//        for (StockInfoEntity stock : stocks) {
//
//        }
        return null;
    }

    private List<String> convertSeedsToIndex(List<String> seeds) {
        List<String> result = new ArrayList<>();
        for (String seed : seeds) {
            String stockIndex = seed.substring(0, 3);
            result.add(Integer.parseInt(stockIndex) + "");
        }
        return result;
    }

//    private List<StockInfoEntity> getStockInfoEntitiesUsingIndex(List<String> indices) {
//        List<StockInfoEntity> result = new ArrayList<>();
//
//        List<StockInfoEntity> allStocks = stockInfoRepository.findAllByOrderByStockCode();
//        for (String index : indices) {
//            result.add(allStocks.get(index));
//        }
//
//        return result;
//    }

//    private double calculateAverage(StockInfoEntity stockInfo) {
//        double data = 0;
//        for (int i = 0 ; i < stockInf) {
//
//        }
//    }

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
