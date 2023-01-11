package com.mtg.Motugame.stock.repository;

import com.mtg.Motugame.entity.StockInfoEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@DataJpaTest
@ActiveProfiles("h2")
class StockInfoRepositoryTest {

    @Autowired
    private StockInfoRepository stockInfoRepository;

    @Test
    @DisplayName("모든 주식 정보 가져오기 테스트")
    public void findAll() {
        //given
        StockInfoEntity stockInfo = StockInfoEntity.builder()
                .stockCode("123456")
                .stockName("삼성전자")
                .build();
        stockInfoRepository.save(stockInfo);

        //when
        List<StockInfoEntity> stockList = stockInfoRepository.findAll();

        //then
        Assertions.assertThat(stockList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("주식에 대해서 주식코드로 정렬해서 가져오는 로직이 제대로 작동하는지")
    public void findSortAllData() {
        //given
        StockInfoEntity stockInfo1 = StockInfoEntity.builder()
                .stockCode("2")
                .stockName("삼성전자")
                .build();
        StockInfoEntity stockInfo2 = StockInfoEntity.builder()
                .stockCode("1")
                .stockName("SK하이닉스")
                .build();
        stockInfoRepository.save(stockInfo1);
        stockInfoRepository.save(stockInfo2);

        //when
        List<StockInfoEntity> findStocks = stockInfoRepository.findAllByOrderByStockCode();

        //then
        Assertions.assertThat(findStocks.get(0).getStockName()).isEqualTo("SK하이닉스");
    }

}