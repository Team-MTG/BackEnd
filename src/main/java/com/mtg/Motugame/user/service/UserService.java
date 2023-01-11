package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserEntity findUser(String id);
    List<UserEntity> findAllUser();
    UserEntity insertUser(UserDto userDto);
}
