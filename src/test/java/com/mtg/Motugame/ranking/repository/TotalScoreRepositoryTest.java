package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.TotalScoreEntity;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
class TotalScoreRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TotalScoreRepository totalScoreRepository;

    @Test
    @DisplayName("profit에 대해서 내림차순으로 정렬되어 값들이 불러지는지 확인")
    void checkFindAllByOrderByProfitDesc() {
        // given
        UserEntity entity1 = UserEntity.builder().nickname("haechan").build();
        UserEntity entity2 = UserEntity.builder().nickname("jiwon").build();
        List<TotalScoreEntity> totalScoreEntities = List.of(
                TotalScoreEntity.builder().totalYield(new BigDecimal(1294292)).profit(new BigDecimal("54.2"))
                        .user(entity1).build(),
                TotalScoreEntity.builder().totalYield(new BigDecimal(21491242)).profit(new BigDecimal("74.2"))
                        .user(entity2).build()
        );
        userRepository.save(entity1);
        userRepository.save(entity2);
        for (TotalScoreEntity totalScore : totalScoreEntities) {
            totalScoreRepository.save(totalScore);
        }

        //when
        List<TotalScoreEntity> findData = totalScoreRepository.findAllByOrderByProfitDesc();

        //then
        Assertions.assertThat(findData.get(0).getUser().getNickname()).isEqualTo("jiwon");
        Assertions.assertThat(findData.get(1).getUser().getNickname()).isEqualTo("haechan");
    }
}