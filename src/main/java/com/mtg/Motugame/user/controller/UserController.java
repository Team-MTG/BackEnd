package com.mtg.Motugame.user.controller;

import com.mtg.Motugame.user.dto.UserDto;
import com.mtg.Motugame.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserController {

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> findAllUser(){
        return null;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> InsertUser(@RequestBody UserDto userDto){
        return null;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String id){
       return null;
    }


}
