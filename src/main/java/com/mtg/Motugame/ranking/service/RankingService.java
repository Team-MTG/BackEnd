package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;

import java.math.BigDecimal;

public interface RankingService {
    void saveScore(RankRequestDto rankRequestDto);
    Long getRank(String nickname, BigDecimal profit);
}
