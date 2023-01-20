package com.mtg.Motugame.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankRequestDto {

    @NotBlank(message = "nickname은 보내야하는 값 입니다.")
    private String nickname;

    @NotNull(message = "totalProfit 값은 항상 있어야합니다. ")
    private Double totalProfit;

    private Double totalYield;

    private List<GameInfo> gameInfo;
}
