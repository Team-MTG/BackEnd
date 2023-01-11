package com.mtg.Motugame.stock.repository;

import com.mtg.Motugame.entity.StockInfoEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

}