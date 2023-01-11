//package com.mtg.Motugame.stock.service;
//
//import com.mtg.Motugame.entity.StockInfoEntity;
//import com.mtg.Motugame.entity.StockPriceEntity;
//import com.mtg.Motugame.stock.dto.StockDataInfoDto;
//import com.mtg.Motugame.stock.dto.StockDatePriceDto;
//import com.mtg.Motugame.stock.repository.StockInfoRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import static org.mockito.BDDMockito.given;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class StockServiceImplTest {
//    @InjectMocks
//    private StockServiceImpl stockService;
//
//    @Mock
//    private StockInfoRepository stockInfoRepository;
//
//    @DisplayName("주식가격 가져오기")
//    @Test
//    void getStocksPrices() {
//        //given
//        List<String> randNum = List.of("0011", "0021");
//        given(stockInfoRepository.findAll()).willReturn(stockInfoEntityList());
//
//        //when
//        List<StockDataInfoDto> stocksPrices = stockService.getStocksPrices(randNum);
//
//        //then
//        Assertions.assertThat(stocksPrices.size()).isEqualTo(2);
//    }
//
//    private List<StockInfoEntity> stockInfoEntityList() {
//        List<StockInfoEntity> stockInfoEntityList = new ArrayList<>();
//        for (int i = 0; i <= 20; i++) {
//            stockInfoEntityList.add(StockInfoEntity.builder()
//                    .stockCode("test code")
//                    .stockName("test name")
//                    .stockPriceEntities(stockPriceEntityList())
//                    .build()
//            );
//        }
//        return stockInfoEntityList;
//    }
//
//    private List<StockPriceEntity> stockPriceEntityList() {
//        List<StockPriceEntity> stockPriceEntityList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            stockPriceEntityList.add(StockPriceEntity.builder()
//                    .id(1L)
//                    .date(LocalDate.now())
//                    .stockInfo(new StockInfoEntity())
//                    .low(BigDecimal.valueOf(0))
//                    .open(BigDecimal.valueOf(0))
//                    .close(BigDecimal.valueOf(0))
//                    .high(BigDecimal.valueOf(1))
//                    .changePrice(BigDecimal.valueOf(10F))
//                    .build()
//            );
//        }
//        return stockPriceEntityList;
//    }
//}