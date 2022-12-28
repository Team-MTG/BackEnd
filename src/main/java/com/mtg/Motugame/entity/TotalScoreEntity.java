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
@Table(name = "total_score")
public class TotalScoreEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal profit;

    @Column(name = "total_yield", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalYield;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
