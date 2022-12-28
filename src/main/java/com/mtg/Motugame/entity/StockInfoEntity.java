package com.mtg.Motugame.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "stock_info", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_STOCKNAME", columnNames = {"stock_name"})})
public class StockInfoEntity {
    @Id
    @Column(name = "stock_code", length = 30)
    private String stockCode;

    @Column(name = "stock_name", length = 50, nullable = false)
    private String stockName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "stockInfo")
    List<StockPriceEntity> stockPriceEntities = new ArrayList<>();
}
