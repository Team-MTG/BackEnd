package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;

import java.util.List;

public interface RankingService {
    public List<RankResponseDto> getSortedRank(int cnt);
}
