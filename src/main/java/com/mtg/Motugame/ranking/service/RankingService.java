package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.RankSharingResponseDto;

import java.util.List;

public interface RankingService {

    Long saveScore(RankRequestDto rankRequestDto);

    RankResponseDto getRank(RankRequestDto rankRequestDto, Long sharedNumber);

    List<RankResponseDto> getSortedRank(int cnt);

    RankSharingResponseDto getRankSharing(Long sharedNumber);
}
