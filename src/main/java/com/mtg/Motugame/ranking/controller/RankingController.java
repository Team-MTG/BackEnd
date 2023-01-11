package com.mtg.Motugame.ranking.controller;

import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;

import com.mtg.Motugame.ranking.service.RankingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
        rankingService.saveScore(rankRequestDto);
        return ResponseEntity.ok().body(rankingService.getRank(rankRequestDto));
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<RankResponseDto>> getRank(@RequestParam("start") int cnt) {
        List<RankResponseDto> list = rankingService.getSortedRank(cnt);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", Integer.toString(rankingService.getHeadRank()));

        return ResponseEntity.ok().headers(headers).body(list);
    }

    @RequestMapping(value = "/rankings", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getHeadRank() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Total-Count", Integer.toString(rankingService.getHeadRank()));

        return ResponseEntity.ok().headers(headers).body(null);
    }
}
