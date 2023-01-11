package com.mtg.Motugame.stock.repository;

import com.mtg.Motugame.entity.StockPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPriceEntity, Long> {
}
