package com.mtg.Motugame.user.service;

import com.mtg.Motugame.user.dto.UserDto;

public interface UserService {
    public UserDto findUser(String id);
}
