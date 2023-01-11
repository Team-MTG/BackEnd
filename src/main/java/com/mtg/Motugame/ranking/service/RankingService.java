package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.TotalInfoDto;

import java.util.List;

public interface RankingService {
    public void recordScore(RankRequestDto rankRequestDto);
}
