package com.mtg.Motugame.ranking.controller;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.service.RankingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class RankingController {

    private final RankingServiceImpl rankingService;

    @PostMapping("/rankings")
    public ResponseEntity<RankResponseDto> gameResult(@RequestBody RankRequestDto rankRequestDto){
        rankingService.saveScore(rankRequestDto);

        return ResponseEntity.ok().body(rankingService.getRank(rankRequestDto));
    }

}
