package com.mtg.Motugame.stock.repository;

import com.mtg.Motugame.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockInfoRepository extends JpaRepository<StockInfoEntity, String> {
    public List<StockInfoEntity> findAllByOrderByStockCode();

    public StockInfoEntity findByStockCode(String code);

    Optional<StockInfoEntity> findByStockName(String stockName);
}
