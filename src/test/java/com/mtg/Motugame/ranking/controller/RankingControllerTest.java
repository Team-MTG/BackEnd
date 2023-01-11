package com.mtg.Motugame.ranking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.ScoreInfo;
import com.mtg.Motugame.ranking.service.RankingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RankingController.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class RankingControllerTest {
    @MockBean
    private RankingServiceImpl rankingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("내 랭킹을 확인하는 api가 제대로 동작하는지")
    void getRankingSuccessfully() throws Exception {
        //given
        RankRequestDto request = RankRequestDto.builder()
                .nickname("jiwon")
                .totalProfit(new BigDecimal("54.2"))
                .totalYield(new BigDecimal(15315147))
                .scoreInfoList(List.of(
                        ScoreInfo.builder().stockCode("00001").profit(new BigDecimal("23.4")).yield(new BigDecimal(153124)).build(),
                        ScoreInfo.builder().stockCode("00002").profit(new BigDecimal("43.4")).yield(new BigDecimal(1242124)).build()

                )).build();

        RankResponseDto response = RankResponseDto.builder()
                .rank(1L)
                .nickname("jiwon")
                .profit(new BigDecimal("54.2"))
                .yield(new BigDecimal(15315147))
                .build();
        given(rankingService.getRank(any()))
                .willReturn(response);

        //when
        mockMvc.perform(
                        post("/api/rankings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}