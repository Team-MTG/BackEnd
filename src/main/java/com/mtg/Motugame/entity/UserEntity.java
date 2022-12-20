package com.mtg.Motugame.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    private String id;

    private String name;

    private String gameId;
}
