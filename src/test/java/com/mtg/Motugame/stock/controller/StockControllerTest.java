package com.mtg.Motugame.stock.controller;

import com.mtg.Motugame.entity.StockInfoEntity;
import com.mtg.Motugame.stock.dto.StockDataInfoDto;
import com.mtg.Motugame.stock.dto.StockDatePriceDto;
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
    @DisplayName("랜덤주식 데이터 가져오기 성공")
    public void getRandomStockSuccess() throws Exception{
        //given
        //인덱스 번호 지정
        List<String> indices = new ArrayList<>();
        indices.add("0011");
        indices.add("0021");

        //요청 데이터(주식가격리스트) 설정
        List<StockDatePriceDto> stockDatePriceDtoList = new ArrayList<>();
        stockDatePriceDtoList.add(StockDatePriceDto.builder()
                .date(LocalDate.now())
                .price(new BigDecimal(10000)).build());
        stockDatePriceDtoList.add(StockDatePriceDto.builder()
                .date(LocalDate.now().minusDays(1))
                .price(new BigDecimal(20000)).build());

        //요청 데이터 (주식코드,주식이름) + 주식가격리스트
        List<StockPriceDto> stockPriceDtoList = new ArrayList<>();
        stockPriceDtoList.add(StockPriceDto.builder()
                        .stockCode("035420")
                        .stockName("네이버")
                        .datas(stockDatePriceDtoList).build());

        given(stockService.getStocksPrices(indices)).willReturn(stockPriceDtoList);

        //when
        mockMvc.perform(get("/api/stocks?index=0011&index=0021"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].stockCode").exists())
                .andExpect(jsonPath("$.[0].stockName").exists())
                .andExpect(jsonPath("$.[0].datas").exists())
                .andDo(print());
        //then
        verify(stockService).getStocksPrices(indices);
    }

    @Test
    @DisplayName("랜덤주식 데이터 가져오기 실패")
    public void getRandomStockfail() throws Exception{
        //given

        //랜덤 인덱스 번호 지정
        List<String> indices = new ArrayList<>();
        indices.add("0011");
        indices.add("0021");

        //요청 데이터 주식가격 설정
        List<StockDatePriceDto> stockDatePriceDtoList = new ArrayList<>();
        stockDatePriceDtoList.add(StockDatePriceDto.builder()
                .date(LocalDate.now())
                .price(new BigDecimal(10000)).build());
        stockDatePriceDtoList.add(StockDatePriceDto.builder()
                .date(LocalDate.now().minusDays(1))
                .price(new BigDecimal(20000)).build());

        //요청 데이터 (주식코드,주식이름) + 주식가격리스트
        List<StockPriceDto> stockPriceDtoList = new ArrayList<>();
        stockPriceDtoList.add(StockPriceDto.builder()
                        .stockCode("035420")
                        .stockName("네이버")
                        .datas(stockDatePriceDtoList).build());

        given(stockService.getStocksPrices(indices)).willReturn(stockPriceDtoList);

        //when
        mockMvc.perform(get("/api/stocks?seed=0011&seed=0021"))//1,26과 불일치하므로 비어있는 body 체크
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").doesNotExist())
                .andDo(print());
        //then
        verify(stockService, atLeastOnce()).getStocksPrices(Arrays.asList("0011","0021"));//정상적으로 동작했는지 검증
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