package com.mtg.Motugame.stock.repository;

import com.mtg.Motugame.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, String> {
    public List<StockInfoEntity> findAllByOrderByStockCode();
}
