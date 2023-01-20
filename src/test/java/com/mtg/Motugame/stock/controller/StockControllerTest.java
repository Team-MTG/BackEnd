package com.mtg.Motugame.stock.controller;

import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.stock.dto.StockAverageDto;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.dto.StockDatePriceDto;
import com.mtg.Motugame.stock.dto.StockPriceDto;
import com.mtg.Motugame.stock.service.StockServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(StockController.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class StockControllerTest {

    @MockBean
    private StockServiceImpl stockService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("랜덤주식 데이터 가져오는 api 정상동작 확인")
    public void getRandomStockSuccess() throws Exception{
        //given
        //인덱스 번호 지정
        List<String> seeds = new ArrayList<>();
        seeds.add("0011");
        seeds.add("2002");
        seeds.add("4004");

        //각 주식의 평균 수익률 정보
        List<StockAverageDto> stockAverageDtoList = new ArrayList<>();

        StockAverageDto stockAverageDto1 = StockAverageDto.builder()
                .stockName("삼성전자")
                .userAverageProfit(3.32).build();
        StockAverageDto stockAverageDto2 = StockAverageDto.builder()
                .stockName("플랙티컬")
                .userAverageProfit(100.32).build();
        StockAverageDto stockAverageDto3 = StockAverageDto.builder()
                .stockName("CJ")
                .userAverageProfit(2.32).build();

        stockAverageDtoList.add(stockAverageDto1);
        stockAverageDtoList.add(stockAverageDto2);
        stockAverageDtoList.add(stockAverageDto3);

        //각 주식의 가격 정보
        List<StockPriceDto> stockPriceDtoList = new ArrayList<>();

        List<StockDatePriceDto> datas1 = new ArrayList<>();
        datas1.add(StockDatePriceDto.builder()
                .date(LocalDate.now())
                .price(BigDecimal.valueOf(100.12)).build());
        datas1.add(StockDatePriceDto.builder()
                .date(LocalDate.now().plusDays(1))
                .price(BigDecimal.valueOf(200.12)).build());
        StockPriceDto stockPriceDto1 = StockPriceDto.builder()
                .stockName("삼성전자")
                .stockCode("005930")
                .datas(datas1).build();

        List<StockDatePriceDto> datas2 = new ArrayList<>();
        datas2.add(StockDatePriceDto.builder()
                .date(LocalDate.now())
                .price(BigDecimal.valueOf(100.12)).build());
        datas2.add(StockDatePriceDto.builder()
                .date(LocalDate.now().plusDays(1))
                .price(BigDecimal.valueOf(200.12)).build());
        StockPriceDto stockPriceDto2 = StockPriceDto.builder()
                .stockName("플랙티컬")
                .stockCode("000001")
                .datas(datas2).build();

        List<StockDatePriceDto> datas3 = new ArrayList<>();
        datas3.add(StockDatePriceDto.builder()
                .date(LocalDate.now())
                .price(BigDecimal.valueOf(100.12)).build());
        datas3.add(StockDatePriceDto.builder()
                .date(LocalDate.now().plusDays(1))
                .price(BigDecimal.valueOf(200.12)).build());
        StockPriceDto stockPriceDto3 = StockPriceDto.builder()
                .stockName("CJ")
                .stockCode("001040")
                .datas(datas3).build();

        stockPriceDtoList.add(stockPriceDto1);
        stockPriceDtoList.add(stockPriceDto2);
        stockPriceDtoList.add(stockPriceDto3);

        //각 게임에 필요한 모든 주식정보를 담음
        StockDataInfoDto stockDataInfoDto = StockDataInfoDto.builder()
                .stockAverages(stockAverageDtoList)
                .stockPrices(stockPriceDtoList).build();

        given(stockService.getStockDataInfoDto(seeds)).willReturn(stockDataInfoDto);

        //when
        mockMvc.perform(get("/api/stocks?seed=0011&seed=2002&seed=4004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockAverages[0].stockName").exists())
                .andExpect(jsonPath("$.stockAverages[0].userAverageProfit").exists())
                .andExpect(jsonPath("$.stockPrices[0].stockCode").exists())
                .andExpect(jsonPath("$.stockPrices[0].stockName").exists())
                .andExpect(jsonPath("$.stockPrices[0].datas[0].date").exists())
                .andExpect(jsonPath("$.stockPrices[0].datas[0].price").exists())
                .andExpect(jsonPath("$.stockPrices[0].datas[1].date").exists())
                .andExpect(jsonPath("$.stockPrices[0].datas[1].price").exists())
                .andExpect(jsonPath("$.stockAverages[1].stockName").exists())
                .andExpect(jsonPath("$.stockAverages[1].userAverageProfit").exists())
                .andExpect(jsonPath("$.stockPrices[1].stockCode").exists())
                .andExpect(jsonPath("$.stockPrices[1].stockName").exists())
                .andExpect(jsonPath("$.stockPrices[1].datas[0].date").exists())
                .andExpect(jsonPath("$.stockPrices[1].datas[0].price").exists())
                .andExpect(jsonPath("$.stockPrices[1].datas[1].date").exists())
                .andExpect(jsonPath("$.stockPrices[1].datas[1].price").exists())
                .andExpect(jsonPath("$.stockAverages[2].stockName").exists())
                .andExpect(jsonPath("$.stockAverages[2].userAverageProfit").exists())
                .andExpect(jsonPath("$.stockPrices[2].stockCode").exists())
                .andExpect(jsonPath("$.stockPrices[2].stockName").exists())
                .andExpect(jsonPath("$.stockPrices[2].datas[0].date").exists())
                .andExpect(jsonPath("$.stockPrices[2].datas[0].price").exists())
                .andExpect(jsonPath("$.stockPrices[2].datas[1].date").exists())
                .andExpect(jsonPath("$.stockPrices[2].datas[1].price").exists())
                .andDo(print());
        //then
        verify(stockService).getStockDataInfoDto(seeds);
    }

    @Test
    @DisplayName("헤더에 주식데이터 개수 담기 성공")
    public void getHeadRandomStockSuccess() throws Exception{
        //given

        //요청 데이터 (주식코드,주식이름) + 주식가격리스트
        List<StockInfoEntity> stockDataInfoList = stockInfoList();

        given(stockService.getStocksInfo()).willReturn(stockDataInfoList.size());

        //when
        mockMvc.perform(head("/api/stocks")) //request의 헤더에 추가하는 것이다.
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(stockService).getStocksInfo();
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
}
