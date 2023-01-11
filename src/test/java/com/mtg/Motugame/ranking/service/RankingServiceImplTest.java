package com.mtg.Motugame.ranking.service;

import com.mtg.Motugame.entity.RankingEntity;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.repository.RankingRepository;
import com.mtg.Motugame.user.repository.UserRepository;
import com.mtg.Motugame.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RankingServiceImplTest {
    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingServiceImpl rankingService;

    @Test
    @DisplayName("랭킹 목록 가져오기")
    void getRank(){
        //given
//        RankingEntity ranking1 = new RankingEntity((long)1,"jiwon",(float)40.3,6000000);
//        RankingEntity ranking2 = new RankingEntity((long)2,"minsun",(float)30,5000000);
//        List<RankingEntity> list= new ArrayList<>(Arrays.asList(ranking1,ranking2));
//        given(rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"))).willReturn(list);
//
//        //when
//        List<RankResponseDto> list2 = rankingService.getRank();
//
//        //then
//        Assertions.assertThat(list2).isNotEmpty();
//        Assertions.assertThat(list2.size()).isEqualTo(2);
//        Assertions.assertThat(list2.get(0).getName()).isEqualTo("jiwon");
//        Assertions.assertThat(list2.get(0).getRank()).isEqualTo(1);
//        Assertions.assertThat(list2.get(1).getName()).isEqualTo("minsun");
//        Assertions.assertThat(list2.get(1).getRank()).isEqualTo(2);
    }

    @Test
    @DisplayName("랭킹 등록")
    void insertRank(){
        //given
//        RankingEntity ranking1 = new RankingEntity((long)1,"jiwon",(float)30,6000000);
//        RankingEntity ranking2 = new RankingEntity((long)2,"minsun",(float)30,5000000);
//        List<RankingEntity> list= new ArrayList<>(Arrays.asList(ranking1,ranking2));
//        given(rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCash"))).willReturn(list);
//        RankRequestDto request = RankRequestDto.builder()
//                .name("jiwon")
//                .rate(30)
//                .totalCash(6000000)
//                .build();
//
//        doReturn(new RankingEntity((long)1,request.getName(),request.getRate(),request.getTotalCash()))
//                .when(rankingRepository)
//                .save(any(RankingEntity.class));
//
//        //when
//        RankResponseDto rank = rankingService.insertRank(request);
//
//        //then
//        Assertions.assertThat(rank).isNotNull();
//        Assertions.assertThat(rank.getName()).isEqualTo("jiwon");
//
//        //verify
//        verify(rankingRepository,times(1)).save(any(RankingEntity.class));


    }
}