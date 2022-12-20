package com.mtg.Motugame.user.controller;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.service.UserService;
import com.mtg.Motugame.user.service.UserServiceImplA;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserController {

    private final UserServiceImplA userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserEntity>> findAllUser(){
        List<UserEntity> users = userService.findAllUser();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserEntity> InsertUser(@RequestBody UserDto userDto){
        return null;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserEntity> findUserById(@PathVariable String id){
        UserEntity userEntity = userService.findUser(id);
        
        return ResponseEntity.ok().body(userEntity);
    }


}
