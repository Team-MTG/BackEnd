package com.mtg.Motugame.ranking.repository;

import com.mtg.Motugame.entity.TotalScoreEntity;
import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.ranking.dto.RankSqlResultDto;
import com.mtg.Motugame.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;


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
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(1294292)).profit(BigDecimal.valueOf(54.2))
                        .user(entity1).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(21491242)).profit(BigDecimal.valueOf(74.2))
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

    @Test
    @DisplayName("정렬된 주식 랭킹 5개 조회 성공 테스트")
    public void findSortedRankSuccess() {
        //given

        List<TotalScoreEntity> totalScoreEntities = List.of(
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(12942920)).profit(BigDecimal.valueOf(54.2))
                        .id(1L).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(21491242)).profit(BigDecimal.valueOf(74.2))
                        .id(2L).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(13942920)).profit(BigDecimal.valueOf(55.2))
                        .id(3L).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(20491242)).profit(BigDecimal.valueOf(73.2))
                        .id(4L).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(20000000)).profit(BigDecimal.valueOf(70.2))
                        .id(5L).build(),
                TotalScoreEntity.builder().totalYield(BigDecimal.valueOf(15000000)).profit(BigDecimal.valueOf(60.2))
                        .id(6L).build()
        );

        totalScoreRepository.saveAll(totalScoreEntities);


        //when
        List<RankSqlResultDto> list = totalScoreRepository.findRank(0);

        //then
        Assertions.assertThat(list.size()).isEqualTo(5);
        Assertions.assertThat(list.get(0).getId()).isEqualTo(2);
        Assertions.assertThat(list.get(1).getId()).isEqualTo(4);
        Assertions.assertThat(list.get(2).getId()).isEqualTo(5);
        Assertions.assertThat(list.get(3).getId()).isEqualTo(6);
        Assertions.assertThat(list.get(4).getId()).isEqualTo(3);

    }
}