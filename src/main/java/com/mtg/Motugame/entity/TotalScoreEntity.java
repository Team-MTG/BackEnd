package com.mtg.Motugame.entity;

import com.mtg.Motugame.ranking.dto.RankSqlResultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedNativeQuery(
        name = "findRank",
        query =
                "SELECT id, user_id as userId, profit, total_yield as totalYield, row_number() over(order by profit desc) as num\n" +
                        "FROM total_score Order By num limit :end offset :start",
        resultSetMapping = "ResultMapper"
)
@SqlResultSetMapping(
        name = "ResultMapper",
        classes = {@ConstructorResult(
                targetClass = RankSqlResultDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "profit", type = BigDecimal.class),
                        @ColumnResult(name = "totalYield", type = BigDecimal.class),
                        @ColumnResult(name = "num", type = Integer.class)
                }
        )}
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "total_score")
public class TotalScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal profit;

    @Column(name = "total_yield", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalYield;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "totalScore")
    List<ScoreRecordEntity> scoreRecords = new ArrayList<>();

}
