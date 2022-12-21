package com.mtg.Motugame.ranking.controller;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
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
    public ResponseEntity<RankResponseDto> insertRank(@RequestBody RankRequestDto rankRequestDto){
        RankResponseDto rankResponseDto = rankingService.insertRank(rankRequestDto);

        return ResponseEntity.ok().body(rankResponseDto);
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<RankResponseDto>> getRank(){
        List<RankResponseDto> list = rankingService.getRank();

        return ResponseEntity.ok().body(list);
    }
}
