package com.mtg.Motugame.user.controller;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.repository.UserRepository;
import com.mtg.Motugame.user.service.UserService;
import com.mtg.Motugame.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Autowired
    private MockMvc mockMvc;

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
//        List<UserEntity> userList = new ArrayList<>();
//        userList.add(new UserEntity("해찬123","해찬","광운대1짱"));
//        userList.add(new UserEntity("강호123","강호","광운대2짱"));
//        userList.add(new UserEntity("서진123","서진","광운대-1짱"));
//        userList.add(new UserEntity("","",""));
        given(userRepository.findAll()).willReturn(null);

        //when
        mockMvc.perform(
                        get("/api/users"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
        verify(userRepository, atLeastOnce()).findAll();
    }

//    @Test
//    public void findUser_success() throws Exception {
//        //given
//        UserEntity user = UserEntity.builder()
//                .id("해찬123")
//                .name("유해찬")
//                .gameId("해찬")
//                .build();
//        given(userService.findUser(anyString())).willReturn(user);
//
//        //when
//        mockMvc.perform(
//                        get("/api/users/gwe123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.name").exists())
//                .andExpect(jsonPath("$.gameId").exists())
//                .andDo(print());
//
//        //then
//        verify(userService, atLeastOnce()).findUser(anyString());
//    }

//    @Test
//    public void findUser_fail() throws Exception{
//        //given
//        given(userService.findAllUser()).willReturn(userList);
//
//        //when
//        mockMvc.perform(
//                        get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0].id").exists())
//                .andExpect(jsonPath("$.[0].name").exists())
//                .andExpect(jsonPath("$.[0].gameId").exists())
//                .andDo(print());
//
//        //then
//        verify(userService,atLeastOnce()).findAllUser();
//    }

}