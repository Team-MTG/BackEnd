package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;

import java.math.BigDecimal;

public interface RankingService {
    void saveScore(RankRequestDto rankRequestDto);
    RankResponseDto getRank(RankRequestDto rankRequestDto);
}
