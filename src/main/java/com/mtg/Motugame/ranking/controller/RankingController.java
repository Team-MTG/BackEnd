package com.mtg.Motugame.ranking.controller;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.TotalInfoDto;
import com.mtg.Motugame.ranking.service.RankingService;
import com.mtg.Motugame.ranking.service.RankingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class RankingController {

    private final RankingServiceImpl rankingService;

    @PostMapping("/rankings")
    public ResponseEntity<RankResponseDto> gameResult(@RequestBody RankRequestDto rankRequestDto){
        rankingService.recordScore(rankRequestDto);

        RankResponseDto rankResponseDto = RankResponseDto.builder()
                .nickname(rankRequestDto.getNickname())
                .profit(rankRequestDto.getTotalProfit())
                .yield(rankRequestDto.getTotalYield())
                .rank(rankingService.getRank(rankRequestDto.getNickname(),rankRequestDto.getTotalProfit()))
                .build();

        return ResponseEntity.ok().body(rankResponseDto);
    }

//    @GetMapping("/rankings")
//    public ResponseEntity<List<RankResponseDto>> getRank(){
//        List<RankResponseDto> list = rankingService.getRank();
//
//        return ResponseEntity.ok().body(list);
//    }
}
