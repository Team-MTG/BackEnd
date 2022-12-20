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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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
        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getGameId()).isEqualTo(user.getGameId());
    }

    private UserEntity user() {
        return new UserEntity("qwd5320", "jiwon", "jiione");
    }
}