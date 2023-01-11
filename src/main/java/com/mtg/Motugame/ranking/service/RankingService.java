package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;

public interface RankingService {
    void saveScore(RankRequestDto rankRequestDto);
}
