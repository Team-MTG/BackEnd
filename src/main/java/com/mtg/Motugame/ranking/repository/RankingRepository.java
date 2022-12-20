package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<RankingEntity,Long> {
}
