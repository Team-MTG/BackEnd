package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserEntity findUser(String id);

    public List<UserEntity> findAllUser();
    public UserEntity insertUser(UserDto userDto);
}
