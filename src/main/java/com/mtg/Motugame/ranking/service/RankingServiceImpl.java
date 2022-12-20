package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.RankingEntity;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService{

    private final RankingRepository rankingRepository;

    public List<RankResponseDto> getRank(){
        List<RankingEntity> users = rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"));

        if(users.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        List<RankResponseDto> list = new ArrayList<>();

        int rank=1;
        for(RankingEntity r : users){
            RankResponseDto.builder()
                    .rank(rank++)
                    .id(r.getId())
                    .name(r.getName())
                    .rate(r.getRate())
                    .totalCash(r.getTotalCash())
                    .build();
        }

        return list;
    }

    @Transactional
    public RankResponseDto insertRank(RankRequestDto rankRequestDto){

        RankingEntity ranking = RankingEntity.builder()
                .name(rankRequestDto.getName())
                .rate(rankRequestDto.getRate())
                .totalCash(rankRequestDto.getTotalCash())
                .build();

        RankingEntity newRank = rankingRepository.save(ranking);

        List<RankingEntity> users = rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"));

        int i=1;
        for(RankingEntity r : users){
            if(r.getId() == newRank.getId()){
                RankResponseDto rankResponseDto = RankResponseDto.builder()
                        .id(newRank.getId())
                        .name(newRank.getName())
                        .rank(i)
                        .totalCash(newRank.getTotalCash())
                        .rate(newRank.getRate())
                        .build();
                return rankResponseDto;
            }
            i++;
        }

        return null;
    }
}
