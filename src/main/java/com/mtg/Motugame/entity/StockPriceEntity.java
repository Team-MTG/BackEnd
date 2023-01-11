package com.mtg.Motugame.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "stock_price")
public class StockPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //FK
    @ManyToOne
    @JoinColumn(name = "stock_code")
    private StockInfoEntity stockInfo;

    private LocalDate date;

    @Column(precision = 15, scale = 2)
    private BigDecimal open;

    @Column(precision = 15, scale = 2)
    private BigDecimal high;

    @Column(precision = 15, scale = 2)
    private BigDecimal low;

    @Column(precision = 15, scale = 2)
    private BigDecimal close;

    @Column(precision = 15, scale = 2)
    private BigDecimal changePrice;
}
