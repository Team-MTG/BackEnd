package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.*;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.GameInfo;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("stock를 확인할 때, stock 데이터가 없는 경우")
    void notExistStockData() {
        RankRequestDto rankRequest = getRecordScoreRequest();

        given(userRepository.findByNickname(any())).willReturn(
                Optional.of(UserEntity.builder()
                        .nickname("김김김").build()));

        given(totalScoreRepository.save(any()))
                .willReturn(TotalScoreEntity.builder()
                        .profit(new BigDecimal(43.4))
                        .totalYield(new BigDecimal(102402))
                        .build());

        given(stockInfoRepository.findById(any()))
                .willReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(()->{
            rankingService.saveScore(rankRequest);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.NO_DATA_ERROR);
    }

    @Test
    @DisplayName("랭킹에 값을 제대로 찾는지 확인")
    void getRankingSuccessfully() {
        //given
        given(totalScoreRepository.findAllByOrderByProfitDesc())
                .willReturn(
                        List.of(
                                TotalScoreEntity.builder()
                                        .profit(new BigDecimal(74.2))
                                        .totalYield(new BigDecimal(742000))
                                        .user(UserEntity.builder()
                                                .id(1L)
                                                .nickname("박지원").build()).build(),
                                TotalScoreEntity.builder()
                                        .profit(new BigDecimal(54.2))
                                        .totalYield(new BigDecimal(542000))
                                        .user(UserEntity.builder()
                                                .id(2L)
                                                .nickname("유해찬").build()).build()
                        )
                );

        //when
        int ranking = rankingService.getRank(RankRequestDto.builder().
                        nickname("박지원").totalProfit(new BigDecimal(74.2)).build())
                .getRank();

        //then
        Assertions.assertThat(ranking).isEqualTo(1);
    }

    @Test
    @DisplayName("랭킹에 값을 제대로 찾는지 확인")
    void getRankingSuccessfullyInDuplicated() {
        //given
        given(totalScoreRepository.findAllByOrderByProfitDesc())
                .willReturn(
                        List.of(
                                TotalScoreEntity.builder()
                                        .profit(new BigDecimal(74.2))
                                        .totalYield(new BigDecimal(742000))
                                        .user(UserEntity.builder()
                                                .id(1L)
                                                .nickname("박지원").build()).build(),
                                TotalScoreEntity.builder()
                                        .profit(new BigDecimal(54.2))
                                        .totalYield(new BigDecimal(542000))
                                        .user(UserEntity.builder()
                                                .id(2L)
                                                .nickname("박지원").build()).build()
                        )
                );

        //when
        int ranking = rankingService.getRank(RankRequestDto.builder().
                nickname("박지원").totalProfit(new BigDecimal(54.2)).build())
                .getRank();

        //then
        Assertions.assertThat(ranking).isEqualTo(2);
    }

    private RankRequestDto getRecordScoreRequest() {
        List<GameInfo> gameInfoList = new ArrayList<>();
        gameInfoList.add(GameInfo.builder()
                .stockCode("000001")
                .profit(new BigDecimal("10.12"))
                .yield(new BigDecimal("100000")).build());

        gameInfoList.add(GameInfo.builder()
                .stockCode("000002")
                .profit(new BigDecimal("18.32"))
                .yield(new BigDecimal("200000")).build());

        gameInfoList.add(GameInfo.builder()
                .stockCode("000003")
                .profit(new BigDecimal("29.12"))
                .yield(new BigDecimal("300000")).build());

        gameInfoList.add(GameInfo.builder()
                .stockCode("000004")
                .profit(new BigDecimal("9.12"))
                .yield(new BigDecimal("400000")).build());

        gameInfoList.add(GameInfo.builder()
                .stockCode("000005")
                .profit(new BigDecimal("10.12"))
                .yield(new BigDecimal("500000")).build());

        RankRequestDto request = RankRequestDto.builder()
                .totalYield(new BigDecimal("1500000"))
                .totalProfit(new BigDecimal("23.12"))
                .nickname("jiwon")
                .scoreInfoList(gameInfoList)
                .build();

        return request;
    }

//    @Test
//    @DisplayName("정렬된 랭킹값을 제대로 담는지 확인")
//    void getSortedRankingSuccessfully() {
//        //given
//        class Test implements RankResponseWrapper{
//
//            @Override
//            public Long getId() {
//                return 1L;
//            }
//
//            @Override
//            public Long getUserId() {
//                return 2L;
//            }
//
//            @Override
//            public BigDecimal getProfit() {
//                return new BigDecimal(10);
//            }
//
//            @Override
//            public BigDecimal getTotalYield() {
//                return new BigDecimal(1557);
//            }
//
//            @Override
//            public Integer getNum() {
//                return 2;
//            }
//        };
//        Test test = new Test();
//        List<RankResponseWrapper> users = new ArrayList<>();
//        List<RankResponseDto> result = new ArrayList<>();
//        users.add(test);
//        given(totalScoreRepository.findRank(anyInt())).willReturn(users);
//        UserEntity userEntity = UserEntity.builder().nickname("KH").build();
//        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userEntity);
//        given(userRepository.findById(anyLong())).willReturn(userEntityOptional);
//        //when
//        result = rankingService.getSortedRank(anyInt());
//        //then
//        Assertions.assertThat(result.get(0).getNickname()).isEqualTo("KH");
//        Assertions.assertThat(result.get(0).getRank()).isEqualTo(2);
//        Assertions.assertThat(result.get(0).getProfit()).isEqualTo(BigDecimal.valueOf(10));
//        Assertions.assertThat(result.get(0).getYield()).isEqualTo(BigDecimal.valueOf(1557));
//
//    }
//    @Test
//    @DisplayName("반환된 랭킹 비어있을 경우")
//    void emptyRank(){
//        //then
//        Exception exception = assertThrows(IllegalArgumentException.class,
//                () -> rankingService.getSortedRank(anyInt()));
//
//        String expectedMessage = "no such data";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
}