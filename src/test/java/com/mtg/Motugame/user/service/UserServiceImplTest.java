package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
        UserEntity user = new UserEntity("qwd5320", "jiwon","jiione");
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        //when
        UserEntity user2 = userService.findUser("");

        //then
        Assertions.assertThat(user2).isNotNull();
        Assertions.assertThat(user2.getId()).isEqualTo("qwd5320");
        Assertions.assertThat(user2.getName()).isEqualTo("jiwon");
        Assertions.assertThat(user2.getGameId()).isEqualTo("jiione");
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
        UserEntity user1 = new UserEntity("qwd5320", "jiwon","jiione");
        UserEntity user2 = new UserEntity("kor1234", "minsun", "sun");
        List<UserEntity> list = new ArrayList<>(Arrays.asList(user1,user2));
        given(userRepository.findAll()).willReturn(list);

        //when
        List<UserEntity> list2 = userService.findAllUser();

        //then
        Assertions.assertThat(list2).isNotEmpty();
        Assertions.assertThat(list2.size()).isEqualTo(0);
        Assertions.assertThat(list2.get(0).getId()).isEqualTo("qwd5320");
        Assertions.assertThat(list2.get(1).getId()).isEqualTo("kor1234");
    }

    @Test
    @DisplayName("유저 목록 찾기 실패 케이스")
    void findAllUserFail(){
        assertThatThrownBy(() -> userService.findAllUser())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessage.NO_DATA_ERROR);
    }


}