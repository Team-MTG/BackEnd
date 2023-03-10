package com.mtg.Motugame.stock.service;

import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.entity.StockPriceEntity;
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
    
    private List<StockInfoEntity> stockInfoList(){
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

    private List<StockInfoEntity> stockInfoEntityList() {
        List<StockInfoEntity> stockInfoEntityList = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            stockInfoEntityList.add(StockInfoEntity.builder()
                    .stockCode("test code")
                    .stockName("test name")
                    .stockPriceEntities(stockPriceEntityList())
                    .build()
            );
        }
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
