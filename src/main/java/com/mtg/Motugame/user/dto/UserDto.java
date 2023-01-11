package com.mtg.Motugame.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserDto {
    private String nickname;
    private String username;
    private String loginId;
}
