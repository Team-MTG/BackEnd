package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.TotalScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalScoreRepository extends JpaRepository<TotalScoreEntity, Long> {
}
