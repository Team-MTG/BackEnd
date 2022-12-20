package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankResponseDto {
    private long id;
    private String name;
    private int rank;
    private float rate;
    private int totalCash;
}
