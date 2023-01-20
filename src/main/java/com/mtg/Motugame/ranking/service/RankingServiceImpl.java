package com.mtg.Motugame.ranking.service;


import com.mtg.Motugame.entity.*;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.ranking.dto.*;
import com.mtg.Motugame.ranking.repository.ScoreRecordRepository;
import com.mtg.Motugame.ranking.repository.TotalScoreRepository;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.mtg.Motugame.entity.TotalScoreEntity;
import com.mtg.Motugame.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final ScoreRecordRepository scoreRecordRepository;

    private final TotalScoreRepository totalScoreRepository;

    private final UserRepository userRepository;

    private final StockInfoRepository stockInfoRepository;


    public void saveScore(RankRequestDto rankRequestDto) {
        List<GameInfo> gameInfoList = rankRequestDto.getGameInfo();

        UserEntity userEntity = saveUser(rankRequestDto);

        TotalScoreEntity totalScore = totalScoreRepository.save(TotalScoreEntity.builder()
                .profit(BigDecimal.valueOf(rankRequestDto.getTotalProfit()))
                .totalYield(BigDecimal.valueOf(rankRequestDto.getTotalYield()))
                .user(userEntity)
                .build());

        for (GameInfo gameInfo : gameInfoList) {
            StockInfoEntity stockInfoEntity = stockInfoRepository.findByStockName(gameInfo.getStockName())
                    .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));

            scoreRecordRepository.save(ScoreRecordEntity.builder()
                    .totalScore(totalScore)
                    .stockInfo(stockInfoEntity)
                    .profit(BigDecimal.valueOf(gameInfo.getProfit()))
                    .yield(BigDecimal.valueOf(gameInfo.getYield()))
                    .build());
        }

        return totalScore.getId();
    }


    @Override
    public RankResponseDto getRank(RankRequestDto rankRequestDto, Long sharedNumber) {
        return RankResponseDto.builder()
                .nickname(rankRequestDto.getNickname())
                .profit(rankRequestDto.getTotalProfit())
                .yield(rankRequestDto.getTotalYield())
                .rank(findRank(rankRequestDto.getNickname(), rankRequestDto.getTotalProfit()))
                .sharedNumber(sharedNumber)
                .build();
    }

    private UserEntity saveUser(RankRequestDto rankRequestDto) {
        Optional<UserEntity> findData = userRepository.findByNickname(rankRequestDto.getNickname());
        if (findData.isEmpty()) {
            return userRepository.save(UserEntity.builder()
                    .nickname(rankRequestDto.getNickname())
                    .build());
        }
        return findData.get();
    }

    public int findRank(String nickname, BigDecimal profit) {
        List<TotalScoreEntity> rankList = totalScoreRepository.findAllByOrderByProfitDesc();
        int rank = 1;

        for (TotalScoreEntity totalScoreEntity : rankList) {
            if (nickname.equals(totalScoreEntity.getUser().getNickname())
                    && profit.equals(totalScoreEntity.getProfit()))
                break;

            rank++;
        }

        return rank;
    }

    @Override
    public List<RankResponseDto> getSortedRank(int cnt) {
        cnt = (cnt - 1) * 5;
        List<RankSqlResultDto> users = totalScoreRepository.findRank(cnt);
        List<RankResponseDto> list = new ArrayList<>();


        if (users.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        for (RankSqlResultDto element : users) {
            Optional<UserEntity> userEntity = userRepository.findById(element.getUserId());
            userEntity.orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));


            RankResponseDto rankResponseDto = RankResponseDto.builder()
                    .rank(element.getNum())
                    .nickname(userEntity.get().getNickname())
                    .profit(element.getProfit())
                    .yield(element.getTotalYield())
                    .build();
            list.add(rankResponseDto);
        }
        return list;
    }

    @Override
    public RankSharingResponseDto getRankSharing(Long sharedNumber) {
        List<TotalScoreEntity> findRankings = totalScoreRepository.findAllByOrderByProfitDesc();

        List<OtherRankDto> otherRankDtos = new ArrayList<>();
        TotalScoreEntity userData = null;
        long userRank = 0L;

        long rank = 1L;
        for (TotalScoreEntity findRanking : findRankings) {
            otherRankDtos.add(OtherRankDto.builder()
                    .nickname(findRanking.getUser().getNickname())
                    .rank(rank)
                    .profit(findRanking.getProfit())
                    .yield(findRanking.getTotalYield()).build());

            if(Objects.equals(findRanking.getId(), sharedNumber)) {
                userData = findRanking;
                userRank = rank;
            }
            rank++;
            if (rank > 30)
                break;
        }

        if (userData == null) {
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        return RankSharingResponseDto.builder()
                .myRank(userRank)
                .nickname(userData.getUser().getNickname())
                .myProfit(userData.getProfit())
                .myYield(userData.getTotalYield())
                .otherRanking(otherRankDtos).build();
    }

    public Integer getHeadRank() {
        return totalScoreRepository.findAll().size();
    }

}
