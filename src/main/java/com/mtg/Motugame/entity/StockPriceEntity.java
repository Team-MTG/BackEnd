package com.mtg.Motugame.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    private Date date;

    private Integer open;

    private Integer high;

    private Integer low;

    private Integer close;

    private Float changePrice;
}
