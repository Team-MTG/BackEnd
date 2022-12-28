package com.mtg.Motugame.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "score_record")
public class ScoreRecordEntity extends CreatedTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 2)
    private BigDecimal profit;

    @Column(precision = 15, scale = 2)
    private BigDecimal yield;

    @ManyToOne
    @JoinColumn(name = "total_score_id")
    private TotalScoreEntity totalScore;

    @ManyToOne
    @JoinColumn(name = "stock_code")
    private StockInfoEntity stockInfo;
}
