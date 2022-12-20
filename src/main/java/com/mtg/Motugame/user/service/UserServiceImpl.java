package com.mtg.Motugame.user.service;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private static final String NO_DATA_ERROR = "no such data";
    public UserEntity findUser(String id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NO_DATA_ERROR));

        return user;
    }

    public List<UserEntity> findAllUser(){
        List<UserEntity> users = userRepository.findAll();
        return users;
    }
}
