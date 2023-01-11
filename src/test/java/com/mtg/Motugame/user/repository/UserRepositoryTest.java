package com.mtg.Motugame.user.repository;

import com.mtg.Motugame.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("특정 유저 조회")
    @Test
    void findByUserId() {
        //given
        userRepository.save(user());

        //when
        Optional<UserEntity> findUser = userRepository.findByLoginId("haechan");

        //then
        assertThat(findUser.get().getUsername()).isEqualTo(user().getUsername());
    }

    @DisplayName("유저 목록 조회")
    @Test
    void findAllUser() {
        //given
        userRepository.save(user());

        //when
        List<UserEntity> userList = userRepository.findAll();

        //then
        assertThat(userList.size()).isEqualTo(1);
    }

    @DisplayName("유저 등록")
    @Test
    void insertUser() {
        //given
        UserEntity user = user();

        //when
        UserEntity savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(savedUser.getLoginId()).isEqualTo(user.getLoginId());
    }

    private UserEntity user() {
        return UserEntity.builder()
                .username("유해찬")
                .nickname("해찬123")
                .loginId("haechan")
                .build();
    }

    @DisplayName("createDate가 제대로 저장이되는지")
    @Test
    void checkCreateDate() {
        //given
        UserEntity user = user();

        //when
        UserEntity savedUser = userRepository.save(user);

        //then
        assertNotNull(savedUser.getCreatedAt());
    }
}