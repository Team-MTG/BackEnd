package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserEntity findUser(String id){
        UserEntity user = userRepository.findByLoginId(id).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));
        return user;
    }

    @Override
    public List<UserEntity> findAllUser(){
        List<UserEntity> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        return users;
    }

    @Override
    public UserEntity insertUser(UserDto userDto){
        if(userRepository.findByNickname(userDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.USER_ALREADY_EXISTS);
        }

        UserEntity user = UserEntity.builder()
                .nickname(userDto.getNickname())
                .build();
        userRepository.save(user);

        return user;
    }
}