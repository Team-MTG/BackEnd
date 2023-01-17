package com.mtg.Motugame.ranking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("h2")
@Transactional
public class RankingRepositoryTest {
    @Autowired
    private TotalScoreRepository totalScoreRepository;

//    @Test
//    @DisplayName("주식 랭킹 조회 성공 테스트")
//    public void findRankSuccess() {
//        //given
//        for (int i = 1; i <= 32; i++) {
//            TotalScoreEntity totalScoreEntity = TotalScoreEntity.builder()
//                    .totalYield(BigDecimal.valueOf(0))
//                    .profit(BigDecimal.valueOf(4 + i))
//                    .user(null)
//                    .build();
//            totalScoreRepository.save(totalScoreEntity);
//        }
//
//        //when
//        List<RankResponseWrapper> list = totalScoreRepository.findRank(1);
//
//        //then
//        Assertions.assertThat(list.size()).isEqualTo(30);
//    }
//
//    @Test
//    @DisplayName("주식 랭킹 조회 실패 테스트")
//    public void findRankFail() {
//        //given
//        for (int i = 1; i <= 28; i++) {
//            TotalScoreEntity totalScoreEntity = TotalScoreEntity.builder()
//                    .totalYield(BigDecimal.valueOf(0))
//                    .profit(BigDecimal.valueOf(4 + i))
//                    .user(null)
//                    .build();
//            totalScoreRepository.save(totalScoreEntity);
//        }
//
//        //when
//        List<RankResponseWrapper> list = totalScoreRepository.findRank(1);
//
//        //then
//        Assertions.assertThat(list.size()).isNotEqualTo(30);
//    }
}
