package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.exception.ExceptionMessage;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    public UserEntity findUser(String id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR));
        return user;
    }

    public List<UserEntity> findAllUser(){
        List<UserEntity> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessage.NO_DATA_ERROR);
        }

        return users;
    }

    public UserEntity insertUser(UserDto userDto){
        if(userRepository.findById(userDto.getId()).isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.USER_ALREADY_EXISTS);
        }

        UserEntity user = UserEntity.builder()
                .id(userDto.getId())
                .gameId(userDto.getGameId())
                .name(userDto.getName())
                .build();
        userRepository.save(user);

        return user;
    }
}
