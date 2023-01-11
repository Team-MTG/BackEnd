package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.TotalScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TotalScoreRepository extends JpaRepository<TotalScoreEntity, Long> {
    List<TotalScoreEntity> findAllByOrderByProfitDesc();
}
