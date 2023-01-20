package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.TotalScoreEntity;

import com.mtg.Motugame.ranking.dto.RankSqlResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TotalScoreRepository extends JpaRepository<TotalScoreEntity, Long> {

    List<TotalScoreEntity> findAllByOrderByProfitDesc();

    @Query(name = "findRank", nativeQuery = true)
    List<RankSqlResultDto> findRank(@Param(value = "start") int start, @Param(value = "end") int end);
}
