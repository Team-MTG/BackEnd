package com.mtg.Motugame.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든유저성공")
    public void findAllUser_success() throws Exception {
        //given
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity("해찬123", "해찬", "광운대1짱"));
        userList.add(new UserEntity("강호123", "강호", "광운대2짱"));
        userList.add(new UserEntity("서진123", "서진", "광운대-1짱"));
        userList.add(new UserEntity("지원123", "지원", "광운대0짱"));
        given(userService.findAllUser()).willReturn(userList);

        //when
        mockMvc.perform(
                        get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").exists())
                .andExpect(jsonPath("$.[0].gameId").exists())
                .andDo(print());

        //then
        verify(userService, atLeastOnce()).findAllUser();
    }


    @Test
    @DisplayName("모든유저실패")
    public void findAllUser_fail() throws Exception {

        // 1. userRespoitory.findAll mock을 통해 NULL을 반환하게 만든다.
        // 2. mvc를 통해서 get요청을 보낸다.
        // 3. 이러면 badrequest보내기 때문에 이를 확인한다.

        //given
        given(userService.findAllUser()).willThrow(new IllegalArgumentException());

        //when
        mockMvc.perform(
                        get("/api/users"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
        verify(userService, atLeastOnce()).findAllUser();
    }
    
    @Test
    @DisplayName("유저 찾기 성공")
    public void findUser_success() throws Exception {
        //given
        UserEntity user = UserEntity.builder()
                .id("해찬123")
                .name("유해찬")
                .gameId("해찬")
                .build();
        given(userService.findUser(anyString())).willReturn(user);

        //when
        mockMvc.perform(
                        get("/api/users/gwe123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.gameId").exists())
                .andDo(print());

        //then
        verify(userService, atLeastOnce()).findUser(anyString());
    }

    @Test
    @DisplayName("유저 찾기 실패")
    public void findUser_fail() throws Exception{
        //given
        given(userService.findUser(anyString())).willThrow(new IllegalArgumentException());

        //when
        mockMvc.perform(
                        get("/api/users/gwe123"))
                .andExpect(status().isBadRequest())
                .andDo(print());
        //then
        verify(userService,atLeastOnce()).findUser(anyString());
    }

    @Test
    @DisplayName("유저 등록 성공")
    public void insertUser_success() throws Exception {
        //given
        UserDto userDto = UserDto.builder()
                .id("해찬123")
                .name("유해찬")
                .gameId("해찬")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id("해찬123")
                .name("유해찬")
                .gameId("해찬")
                .build();

        given(userService.insertUser(any())).willReturn(userEntity);

        String param = objectMapper.writeValueAsString(userDto);
        //when
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(param))
                        .andExpect(status().isOk())
                        .andDo(print());
        //then
        verify(userService, atLeastOnce()).insertUser(any());
    }


    @Test
    @DisplayName("유저 등록 실패")
    public void insertUser_fail() throws Exception {

        //given
        UserDto userDto = UserDto.builder()
                .id("해찬123")
                .name("유해찬")
                .gameId("해찬")
                .build();
        given(userService.insertUser(any())).willThrow(new IllegalArgumentException());
        String param = objectMapper.writeValueAsString(userDto);

        //when
        mockMvc.perform(
                                post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(param))
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
        verify(userService, atLeastOnce()).insertUser(any());
    }
}