package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.repository.UserRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("id로 유저 찾기 성공 테스트")
    void findUserById(){
        //given
        UserEntity user = UserEntity.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build();
        given(userRepository.findByLoginId(any())).willReturn(Optional.of(user));

        //when
        UserEntity user2 = userService.findUser("haechan");

        //then
        Assertions.assertThat(user2).isNotNull();
        Assertions.assertThat(user2.getLoginId()).isEqualTo("haechan");
        Assertions.assertThat(user2.getNickname()).isEqualTo("해찬123");
        Assertions.assertThat(user2.getUsername()).isEqualTo("유해찬");
    }

    @Test
    @DisplayName("id로 유저 찾기 실패 케이스")
    void findUserByIdFail() throws Exception{
        assertThatThrownBy(() -> userService.findUser(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.NO_DATA_ERROR);
    }

    @Test
    @DisplayName("유저 목록 찾기 성공 테스트")
    void findAllUser(){
        //given
        UserEntity user1 = UserEntity.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build();
        UserEntity user2 = UserEntity.builder()
                .loginId("jiwon")
                .nickname("지원123")
                .username("박지원")
                .build();
        List<UserEntity> list = new ArrayList<>(Arrays.asList(user1,user2));
        given(userRepository.findAll()).willReturn(list);

        //when
        List<UserEntity> list2 = userService.findAllUser();

        //then
        Assertions.assertThat(list2).isNotEmpty();
        Assertions.assertThat(list2.size()).isEqualTo(2);
        Assertions.assertThat(list2.get(0).getLoginId()).isEqualTo("haechan");
        Assertions.assertThat(list2.get(1).getLoginId()).isEqualTo("jiwon");
    }

    @Test
    @DisplayName("유저 목록 찾기 실패 케이스")
    void findAllUserFail(){
        assertThatThrownBy(() -> userService.findAllUser())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.NO_DATA_ERROR);
    }

    @Test
    @DisplayName("유저 등록 성공 케이스")
    void insertUser(){
        //given
        UserDto request = UserDto.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build();

        doReturn(UserEntity.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build())
                .when(userRepository)
                .save(any(UserEntity.class));

        //when
        UserEntity user = userService.insertUser(request);

        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getLoginId()).isEqualTo(request.getLoginId());
        Assertions.assertThat(user.getNickname()).isEqualTo(request.getNickname());

        //verify
        verify(userRepository,times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("유저 등록 실패 케이스")
    void insertUserFail(){
        //given
        UserEntity user = UserEntity.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build();

        given(userRepository.findById(any())).willReturn(Optional.of(user));
        UserDto user2 = UserDto.builder()
                .loginId("haechan")
                .nickname("해찬123")
                .username("유해찬")
                .build();

        assertThatThrownBy(() -> userService.insertUser(user2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.USER_ALREADY_EXISTS);
    }
}