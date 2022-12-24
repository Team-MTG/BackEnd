package com.mtg.Motugame.ranking.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankRequestDto {
    private String name;
    private int totalCash;
    private float rate;
}
