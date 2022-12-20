package com.mtg.Motugame.user.controller;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserEntity>> findAllUser(){
        List<UserEntity> users = userService.findAllUser();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserEntity> InsertUser(@RequestBody UserDto userDto){
        UserEntity user = userService.insertUser(userDto);
        if(user==null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok().body(user);
        }
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserEntity> findUserById(@PathVariable String id){
        UserEntity userEntity = userService.findUser(id);

        return ResponseEntity.ok().body(userEntity);
    }


}
