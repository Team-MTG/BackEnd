package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.RankingEntity;
import com.mtg.Motugame.entity.TotalScoreEntity;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.RankResponseWrapper;
import com.mtg.Motugame.ranking.repository.RankingRepository;
import com.mtg.Motugame.ranking.repository.TotalScoreRepository;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final TotalScoreRepository totalScoreRepository;
    private final UserRepository userRepository;

    //무한스크롤에서 순서대로 30개의 랭킹을 출력하는 메서드
    public List<RankResponseDto> getSortedRank(int cnt) {
        List<RankResponseWrapper> users = totalScoreRepository.findRank(cnt); //쿼리문에서 결과 담는 리스트
        List<RankResponseDto> list = new ArrayList<>(); //반환할 리스트

        //결과가 없을 경우 에러
        if (users.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        //리스트 요소 끝까지 반복
        for (RankResponseWrapper element : users) {
            //users 테이블의 id를 통해 엔티티를 찾는다.
            Optional<UserEntity> userEntity = userRepository.findById(element.getUserId());
            //null 예외처리
            userEntity.orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));

            //반활할 dto 가공
            RankResponseDto rankResponseDto = RankResponseDto.builder()
                    .rank(element.getNum())
                    .nickname(userEntity.get().getNickname())
                    .profit(element.getProfit())
                    .yield(element.getTotalYield())
                    .build();
            list.add(rankResponseDto);
        }
        return list;
    }

    public Integer getHeadRank() {
        return totalScoreRepository.findAll().size();
    }

    public RankResponseDto insertRank(RankRequestDto rankRequestDto) {

//        RankingEntity ranking = RankingEntity.builder()
//                .name(rankRequestDto.getName())
//                .rate(rankRequestDto.getRate())
//                .totalCash(rankRequestDto.getTotalCash())
//                .build();
//
//        RankingEntity newRank = rankingRepository.save(ranking);
//
//        List<RankingEntity> users = rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"));
//
//        int i=1;
//        for(RankingEntity ranked : users){
//            if(ranked.getId() == newRank.getId()){
//                RankResponseDto rankResponseDto = RankResponseDto.builder()
//                        .id(newRank.getId())
//                        .name(newRank.getName())
//                        .rank(i)
//                        .totalCash(newRank.getTotalCash())
//                        .rate(newRank.getRate())
//                        .build();
//                return rankResponseDto;
//            }
//            i++;
//        }

        return null;
    }
}
