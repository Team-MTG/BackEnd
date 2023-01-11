
package com.mtg.Motugame.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
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
        List<UserEntity> userList = List.of(
                UserEntity.builder()
                        .loginId("haechan")
                        .nickname("해찬123")
                        .username("유해찬")
                        .build(),
                UserEntity.builder()
                        .loginId("kangho")
                        .nickname("강호123")
                        .username("임강호")
                        .build()
        );
        given(userService.findAllUser()).willReturn(userList);

        //when
        mockMvc.perform(
                        get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nickname").exists())
                .andExpect(jsonPath("$.[0].username").exists())
                .andExpect(jsonPath("$.[0].loginId").exists())
                .andDo(print());

        //then
        verify(userService, atLeastOnce()).findAllUser();
    }


    @Test
    @DisplayName("모든유저실패")
    public void findAllUser_fail() throws Exception {

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
                .username("유해찬")
                .nickname("해찬123")
                .loginId("haechan")
                .build();
        given(userService.findUser(anyString())).willReturn(user);

        //when
        mockMvc.perform(
                        get("/api/users/gwe123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.nickname").exists())
                .andExpect(jsonPath("$.loginId").exists())
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
                .username("유해찬")
                .nickname("해찬123")
                .loginId("haechan")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .username("유해찬")
                .nickname("해찬123")
                .loginId("haechan")
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
                .username("유해찬")
                .nickname("해찬123")
                .loginId("haechan")
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