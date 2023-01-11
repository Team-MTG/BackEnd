//package com.mtg.Motugame.ranking.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mtg.Motugame.entity.RankingEntity;
//import com.mtg.Motugame.ranking.dto.RankRequestDto;
//import com.mtg.Motugame.ranking.dto.RankResponseDto;
//import com.mtg.Motugame.ranking.service.RankingService;
//import com.mtg.Motugame.ranking.service.RankingServiceImpl;
//import com.mtg.Motugame.user.controller.UserController;
//import com.mtg.Motugame.user.service.UserServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@WebMvcTest(RankingController.class)
//@AutoConfigureMockMvc
//class RankingControllerTest {
//    @MockBean
//    private RankingServiceImpl rankingService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("랭킹 목록 보여주기 성공")
//    public void getRankSuccess() throws Exception {
//        //given
//        List<RankResponseDto> rankList = new ArrayList<>();
//        rankList.add(new RankResponseDto((1L, "jiwon", 1, 40F, 9000000));
//        rankList.add(new RankResponseDto((long) 2L, "gwon", 2, 30F, 8000000));
//        rankList.add(new RankResponseDto((long) 3L, "minsun", 3, 20F, 7000000));
//        rankList.add(new RankResponseDto((long) 4L, "sun", 4, 10F, 6000000));
//        given(rankingService.getRank()).willReturn(rankList);
//
//        //when
//        mockMvc.perform(
//                        get("/api/rankings"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0].id").exists())
//                .andExpect(jsonPath("$.[0].name").exists())
//                .andExpect(jsonPath("$.[0].rank").exists())
//                .andExpect(jsonPath("$.[0].totalCash").exists())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("랭킹 목록 보여주기 실패")
//    public void getRankFail() throws Exception {
//
//        //given
//        given(rankingService.getRank()).willThrow(new IllegalArgumentException());
//
//        //when
//        mockMvc.perform(
//                        get("/api/rankings"))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//
//        //then
//        verify(rankingService, atLeastOnce()).getRank();
//    }
//
//    @Test
//    @DisplayName("랭킹 등록 성공")
//    public void insertRankSuccess() throws Exception {
//        //given
//        RankRequestDto rankRequestDto = RankRequestDto.builder()
//                .name("jiwon")
//                .rate(30F)
//                .totalCash(5000000)
//                .build();
//
//        RankResponseDto rankResponseDto = RankResponseDto.builder()
//                .id((long) 1)
//                .name("jiwon")
//                .rate(30F)
//                .totalCash(5000000)
//                .rank(1)
//                .build();
//
//        given(rankingService.insertRank(any())).willReturn(rankResponseDto);
//
//        String param = objectMapper.writeValueAsString(rankRequestDto);
//        //when
//        mockMvc.perform(post("/api/rankings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(param))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        verify(rankingService, atLeastOnce()).insertRank(any());
//    }
//}