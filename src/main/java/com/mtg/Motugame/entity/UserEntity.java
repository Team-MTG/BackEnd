package com.mtg.Motugame.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_NICKNAME_AND_LOGIN_ID",columnNames = {"nickname", "login_id"})})
public class UserEntity extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(name = "login_id", nullable = false)
    private String loginId;

}
