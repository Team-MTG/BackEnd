package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.ScoreRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecordEntity,Long> {
}
