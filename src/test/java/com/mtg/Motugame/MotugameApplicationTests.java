package com.mtg.Motugame;



import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class MotugameApplicationTests {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

	@Test
    public void findAllUser_success() throws Exception{
        //given
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity("해찬123","해찬","광운대1짱"));
        userList.add(new UserEntity("강호123","강호","광운대2짱"));
        userList.add(new UserEntity("서진123","서진","광운대-1짱"));
        userList.add(new UserEntity("지원123","지원","광운대0짱"));
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
        verify(userService,atLeastOnce()).findAllUser();
    }

//    @Test
//    public void findAllUser_fail() throws Exception{
//        //given
//        List<UserEntity> userList = new ArrayList<>();
//        userList.add(new UserEntity("해찬123","해찬","광운대1짱"));
//        userList.add(new UserEntity("강호123","강호","광운대2짱"));
//        userList.add(new UserEntity("서진123","서진","광운대-1짱"));
//        userList.add(new UserEntity("지원123","지원","광운대0짱"));
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

    @Test
    public void findUser_success() throws Exception{
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
        verify(userService,atLeastOnce()).findUser(anyString());
    }

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
