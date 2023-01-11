package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.*;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.ScoreInfo;
import com.mtg.Motugame.ranking.dto.TotalInfoDto;
import com.mtg.Motugame.ranking.repository.RankingRepository;
import com.mtg.Motugame.ranking.repository.ScoreRecordRepository;
import com.mtg.Motugame.ranking.repository.TotalScoreRepository;
import com.mtg.Motugame.stock.repository.StockInfoRepository;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final ScoreRecordRepository scoreRecordRepository;

    private final TotalScoreRepository totalScoreRepository;

    private final UserRepository userRepository;

    private final StockInfoRepository stockInfoRepository;


    public TotalInfoDto recordScore(RankRequestDto rankRequestDto) {

        List<ScoreInfo> scoreInfoList = rankRequestDto.getScoreInfoList();

        if (!userRepository.existsByNickname(rankRequestDto.getNickname())) {
            userRepository.save(UserEntity.builder()
                    .nickname(rankRequestDto.getNickname())
                    .build());
        }

        UserEntity userEntity = userRepository.findByNickname(rankRequestDto.getNickname())
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));


        TotalScoreEntity totalScore = totalScoreRepository.save(TotalScoreEntity.builder()
                .profit(rankRequestDto.getTotalProfit())
                .totalYield(rankRequestDto.getTotalYield())
                .user(userEntity)
                .build());

        for (ScoreInfo scoreInfo : scoreInfoList) {
            StockInfoEntity stockInfoEntity = stockInfoRepository.findById(scoreInfo.getStockCode())
                    .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));

            scoreRecordRepository.save(ScoreRecordEntity.builder()
                    .totalScore(totalScore)
                    .stockInfo(stockInfoEntity)
                    .profit(scoreInfo.getProfit())
                    .yield(scoreInfo.getYield())
                    .build());
        }

        List<TotalScoreEntity> rankList = totalScoreRepository.findAll(Sort.by(Sort.Direction.DESC, "profit"));
        Long rank = 1L;

        for (TotalScoreEntity totalScoreEntity : rankList) {
            if(totalScoreEntity.getUser().getNickname().equals(rankRequestDto.getNickname())
                    && rankRequestDto.getTotalProfit().equals(totalScoreEntity.getProfit()))
                break;

            rank++;
        }

        return TotalInfoDto.builder()
                .profit(rankRequestDto.getTotalProfit())
                .yield(rankRequestDto.getTotalYield())
                .rank(rank).nickname(rankRequestDto.getNickname()).build();
    }

}
