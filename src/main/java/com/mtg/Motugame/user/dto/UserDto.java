package com.mtg.Motugame.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String nickname;
    private String username;
    private String loginId;
}
