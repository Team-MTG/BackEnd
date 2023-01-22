package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.entity.ScoreRecordEntity;
import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.entity.StockPriceEntity;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockInfoRepository stockInfoRepository;

    @DisplayName("주식개수 가져오기 성공")
    @Test
    void getStocksInfoSuccess() {
        //given
        given(stockInfoRepository.findAll()).willReturn(stockInfoList());

        //when
        int size = stockService.getStocksInfo();

        //then
        Assertions.assertThat(size).isEqualTo(20);
    }

    private List<StockInfoEntity> stockInfoList() {
        List<StockInfoEntity> stockInfoEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stockInfoEntityList.add(StockInfoEntity.builder()
                    .stockCode(Integer.toString(i))
                    .stockName("test name")
                    .build()
            );
        }
        return stockInfoEntityList;
    }

    @DisplayName("주식데이터 가져오기 성공")
    @Test
    public void getStockDataInfoSuccess() throws Exception {
        //given
        //parameter seed List
        List<String> seeds = new ArrayList<>();
        seeds.add("0001");
        seeds.add("0011");

        //mocking
        List<StockInfoEntity> stockInfoEntityList = createStockInfoEntity();
        given(stockInfoRepository.findAllByOrderByStockCode()).willReturn(stockInfoEntityList);
        given(stockInfoRepository.findByStockCode("0001")).willReturn(stockInfoEntityList.get(0));
        given(stockInfoRepository.findByStockCode("0011")).willReturn(stockInfoEntityList.get(1));


        //when
        StockDataInfoDto stockDataInfoDto = stockService.getStockDataInfoDto(seeds);

        //then
        Assertions.assertThat(stockDataInfoDto.getStockAverages().get(0).getStockName()).isEqualTo("주식1");
        Assertions.assertThat(stockDataInfoDto.getStockAverages().get(0).getUserAverageProfit()).isEqualTo(10.0);
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(0).getStockName()).isEqualTo("주식1");
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(0).getStockCode()).isEqualTo("0001");
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(0).getDatas().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(10.0));
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(0).getDatas().get(0).getDate()).isEqualTo(LocalDate.of(2023,1,1));
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(0).getDatas().size()).isEqualTo(300);

        Assertions.assertThat(stockDataInfoDto.getStockAverages().get(1).getStockName()).isEqualTo("주식2");
        Assertions.assertThat(stockDataInfoDto.getStockAverages().get(1).getUserAverageProfit()).isEqualTo(10.0);
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(1).getStockName()).isEqualTo("주식2");
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(1).getStockCode()).isEqualTo("0011");
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(1).getDatas().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(10.0));
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(1).getDatas().get(0).getDate()).isEqualTo(LocalDate.of(2023,1,1));
        Assertions.assertThat(stockDataInfoDto.getStockPrices().get(1).getDatas().size()).isEqualTo(300);
    }

    //StockInfoEntity 생성 메서드
    private List<StockInfoEntity> createStockInfoEntity() {
        List<StockInfoEntity> stockInfoEntityList = new ArrayList<>();

        //StockPriceEntity List 생성
        List<StockPriceEntity> stockPriceEntityList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            StockPriceEntity stockPriceEntity1 = StockPriceEntity.builder()
                    .close(BigDecimal.valueOf(10.0))
                    .date(LocalDate.of(2023,1,1)).build();
            stockPriceEntityList.add(stockPriceEntity1);
        }

        //ScoreRecordEntity List 생성
        List<ScoreRecordEntity> scoreRecordEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            scoreRecordEntityList.add(ScoreRecordEntity.builder()
                    .profit(BigDecimal.valueOf(10.0)).build());
        }

        //StockInfoEntity List 생성
        stockInfoEntityList.add(StockInfoEntity.builder()
                .stockCode("0001")
                .stockName("주식1")
                .stockPriceEntities(stockPriceEntityList)
                .scoreRecords(scoreRecordEntityList)
                .build()
        );
        stockInfoEntityList.add(StockInfoEntity.builder()
                .stockCode("0011")
                .stockName("주식2")
                .stockPriceEntities(stockPriceEntityList)
                .scoreRecords(scoreRecordEntityList)
                .build()
        );

        return stockInfoEntityList;
    }

    private List<StockPriceEntity> stockPriceEntityList() {
        List<StockPriceEntity> stockPriceEntityList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stockPriceEntityList.add(StockPriceEntity.builder()
                    .id(1L)
                    .date(LocalDate.now())
                    .stockInfo(new StockInfoEntity())
                    .low(BigDecimal.valueOf(0))
                    .open(BigDecimal.valueOf(0))
                    .close(BigDecimal.valueOf(0))
                    .high(BigDecimal.valueOf(1))
                    .changePrice(BigDecimal.valueOf(10F))
                    .build()
            );
        }
        return stockPriceEntityList;
    }
}
