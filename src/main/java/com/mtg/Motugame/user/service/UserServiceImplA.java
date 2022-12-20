package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplA implements UserService{

    private final UserRepository userRepository;

    public UserEntity findUser(String id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));

        return user;
    }

    public List<UserEntity> findAllUser(){
        List<UserEntity> users = userRepository.findAll();
        return users;
    }
}
