package com.mtg.Motugame.ranking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtg.Motugame.ranking.dto.RankRequestDto;
import com.mtg.Motugame.ranking.dto.RankResponseDto;
import com.mtg.Motugame.ranking.dto.GameInfo;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
                .totalProfit(54.2)
                .totalYield(15315147.0)
                .gameInfo(List.of(
                        GameInfo.builder().stockName("삼성전자").profit(23.4).yield(153124.0).build(),
                        GameInfo.builder().stockName("SK하이닉스").profit(43.4).yield(1242124.0).build()
                )).build();

        RankResponseDto response = RankResponseDto.builder()
                .rank(1)
                .nickname("jiwon")
                .profit(BigDecimal.valueOf(54.2))
                .yield(BigDecimal.valueOf(15315147))
                .build();
        given(rankingService.getRank(any(), any()))
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

    @Test
    @DisplayName("내 랭킹을 알려주는 api 호출시 nickname이 빈 값일때")
    void getRankingEmptyNickname() throws Exception {
        //given
        RankRequestDto request = RankRequestDto.builder()
                .totalProfit(54.2)
                .totalYield(15315147.0)
                .gameInfo(List.of(
                        GameInfo.builder().stockName("삼성전자").profit(23.4).yield(153124.0).build(),
                        GameInfo.builder().stockName("SK하이닉스").profit(43.4).yield(1242124.0).build()
                )).build();

        //when
        mockMvc.perform(
                        post("/api/rankings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("내 랭킹을 알려주는 api 호출시 totalProfit이 빈 값일때")
    void getRankingEmptyTotalProfit() throws Exception {
        //given
        RankRequestDto request = RankRequestDto.builder()
                .nickname("jiwon")
                .totalYield(15315147.0)
                .gameInfo(List.of(
                        GameInfo.builder().stockName("삼성전자").profit(23.4).yield(153124.0).build(),
                        GameInfo.builder().stockName("SK하이닉스").profit(43.4).yield(1242124.0).build()
                )).build();

        //when
        mockMvc.perform(
                        post("/api/rankings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("정렬된 랭킹을 보내는 api가 정상적으로 작동")
    void getSortedRanking() throws Exception {

        List<RankResponseDto> list = new ArrayList<>();

        RankResponseDto response = RankResponseDto.builder()
                .rank(1)
                .nickname("KH")
                .profit(BigDecimal.valueOf(55.5))
                .yield(BigDecimal.valueOf(15555555))
                .build();
        list.add(response);

        given(rankingService.getSortedRank(anyInt()))
                .willReturn(list);
        given(rankingService.getHeadRank()).willReturn(1);

        //when
        mockMvc.perform(get("/api/rankings")
                        .param("start", "1"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(header().exists("Access-Control-Expose-Headers"))
                .andDo(print());
    }

    @Test
    @DisplayName("정렬된 랭킹을 보내는 api 호출 시 start를 안보내주었을 때 ")
    void getSortedRankingErrorStart() throws Exception {
        //when
        mockMvc.perform(get("/api/rankings"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("헤더에 주식데이터 개수 담기 성공")
    public void getHeadRandomStockSuccess() throws Exception {
        //given

        given(rankingService.getHeadRank()).willReturn(0);

        //when
        mockMvc.perform(head("/api/rankings")) //request의 헤더에 추가하는 것이다.
                .andExpect(header().exists("X-Total-Count"))
                .andExpect(header().exists("Access-Control-Expose-Headers"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
