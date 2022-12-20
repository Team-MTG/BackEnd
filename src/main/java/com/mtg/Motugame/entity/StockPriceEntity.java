package com.mtg.Motugame.entity;

import lombok.*;

import javax.persistence.*;
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

    private Integer open;

    private Integer high;

    private Integer low;

    private Integer close;

    private Float changePrice;
}
