package com.mtg.Motugame.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String id;
    private String name;
    private String gameId;
}
