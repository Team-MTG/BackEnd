package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.*;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.ScoreInfo;
import com.mtg.Motugame.ranking.repository.RankingRepository;
import com.mtg.Motugame.ranking.repository.ScoreRecordRepository;
import com.mtg.Motugame.ranking.repository.TotalScoreRepository;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import com.mtg.Motugame.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RankingServiceImplTest {
    @Mock
    private ScoreRecordRepository scoreRecordRepository;

    @Mock
    private TotalScoreRepository totalScoreRepository;

    @Mock
    private StockInfoRepository stockInfoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RankingServiceImpl rankingService;

    @Test
    @DisplayName("랭킹 등록 및 반환 유저가 없는 경우")
    void saveRanking() {
        //given
        RankRequestDto request = getRecordScoreRequest();

        given(userRepository.findByNickname(any())).willReturn(
                Optional.ofNullable(null));

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .nickname(request.getNickname())
                .build();

        doReturn(userEntity)
                .when(userRepository)
                .save(any(UserEntity.class));

        TotalScoreEntity totalScoreEntity = TotalScoreEntity.builder()
                .id(1L)
                .totalYield(request.getTotalYield())
                .profit(request.getTotalProfit())
                .user(userEntity)
                .build();

        doReturn(totalScoreEntity)
                .when(totalScoreRepository)
                .save(any(TotalScoreEntity.class));

        StockInfoEntity stockInfo0 = StockInfoEntity.builder()
                .stockCode(request.getScoreInfoList().get(0).getStockCode())
                .build();

        StockInfoEntity stockInfo1 = StockInfoEntity.builder()
                .stockCode(request.getScoreInfoList().get(1).getStockCode())
                .build();

        StockInfoEntity stockInfo2 = StockInfoEntity.builder()
                .stockCode(request.getScoreInfoList().get(2).getStockCode())
                .build();

        StockInfoEntity stockInfo3 = StockInfoEntity.builder()
                .stockCode(request.getScoreInfoList().get(3).getStockCode())
                .build();

        StockInfoEntity stockInfo4 = StockInfoEntity.builder()
                .stockCode(request.getScoreInfoList().get(4).getStockCode())
                .build();

        given(stockInfoRepository.findById(request.getScoreInfoList().get(0).getStockCode()))
                .willReturn(Optional.of(stockInfo0));

        given(stockInfoRepository.findById(request.getScoreInfoList().get(1).getStockCode()))
                .willReturn(Optional.of(stockInfo1));

        given(stockInfoRepository.findById(request.getScoreInfoList().get(2).getStockCode()))
                .willReturn(Optional.of(stockInfo2));

        given(stockInfoRepository.findById(request.getScoreInfoList().get(3).getStockCode()))
                .willReturn(Optional.of(stockInfo3));

        given(stockInfoRepository.findById(request.getScoreInfoList().get(4).getStockCode()))
                .willReturn(Optional.of(stockInfo4));

        ScoreRecordEntity scoreRecordEntity = ScoreRecordEntity.builder()
                .totalScore(totalScoreEntity)
                .stockInfo(stockInfo0)
                .profit(request.getScoreInfoList().get(0).getProfit())
                .yield(request.getScoreInfoList().get(0).getYield())
                .build();

        doReturn(scoreRecordEntity)
                .when(scoreRecordRepository)
                .save(any(ScoreRecordEntity.class));

        //when
        rankingService.saveScore(request);
    }


//    @Test
//    @DisplayName("랭킹 목록 가져오기")
//    void getRank(){
//        //given
//        RankingEntity ranking1 = new RankingEntity((long)1,"jiwon",(float)40.3,6000000);
//        RankingEntity ranking2 = new RankingEntity((long)2,"minsun",(float)30,5000000);
//        List<RankingEntity> list= new ArrayList<>(Arrays.asList(ranking1,ranking2));
//        given(rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"))).willReturn(list);
//
//        //when
//        List<RankResponseDto> list2 = rankingService.getRank();
//
//        //then
//        Assertions.assertThat(list2).isNotEmpty();
//        Assertions.assertThat(list2.size()).isEqualTo(2);
//        Assertions.assertThat(list2.get(0).getName()).isEqualTo("jiwon");
//        Assertions.assertThat(list2.get(0).getRank()).isEqualTo(1);
//        Assertions.assertThat(list2.get(1).getName()).isEqualTo("minsun");
//        Assertions.assertThat(list2.get(1).getRank()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("랭킹 등록")
//    void insertRank(){
//        //given
//        RankingEntity ranking1 = new RankingEntity((long)1,"jiwon",(float)30,6000000);
//        RankingEntity ranking2 = new RankingEntity((long)2,"minsun",(float)30,5000000);
//        List<RankingEntity> list= new ArrayList<>(Arrays.asList(ranking1,ranking2));
//        given(rankingRepository).willReturn(list);
//        RankRequestDto request = RankRequestDto.builder()
//                .name("jiwon")
//                .rate(30)
//                .totalCash(6000000)
//                .build();
//
//        doReturn(new RankingEntity((long)1,request.getName(),request.getRate(),request.getTotalCash()))
//                .when(rankingRepository)
//                .save(any(RankingEntity.class));
//
//        //when
//        RankResponseDto rank = rankingService.insertRank(request);
//
//        //then
//        Assertions.assertThat(rank).isNotNull();
//        Assertions.assertThat(rank.getName()).isEqualTo("jiwon");
//
//        //verify
//        verify(rankingRepository,times(1)).save(any(RankingEntity.class));
//
//
//    }
    private RankRequestDto getRecordScoreRequest() {
        List<ScoreInfo> scoreInfoList = new ArrayList<>();
        scoreInfoList.add(ScoreInfo.builder()
                .stockCode("000001")
                .profit(new BigDecimal("10.12"))
                .yield(new BigDecimal("100000")).build());

        scoreInfoList.add(ScoreInfo.builder()
                .stockCode("000002")
                .profit(new BigDecimal("18.32"))
                .yield(new BigDecimal("200000")).build());

        scoreInfoList.add(ScoreInfo.builder()
                .stockCode("000003")
                .profit(new BigDecimal("29.12"))
                .yield(new BigDecimal("300000")).build());

        scoreInfoList.add(ScoreInfo.builder()
                .stockCode("000004")
                .profit(new BigDecimal("9.12"))
                .yield(new BigDecimal("400000")).build());

        scoreInfoList.add(ScoreInfo.builder()
                .stockCode("000005")
                .profit(new BigDecimal("10.12"))
                .yield(new BigDecimal("500000")).build());

        RankRequestDto request = RankRequestDto.builder()
                .totalYield(new BigDecimal("1500000"))
                .totalProfit(new BigDecimal("23.12"))
                .nickname("jiwon")
                .scoreInfoList(scoreInfoList)
                .build();

        return request;
    }
}