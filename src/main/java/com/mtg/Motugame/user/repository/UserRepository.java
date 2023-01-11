package com.mtg.Motugame.user.repository;

import com.mtg.Motugame.entity.UserEntity;
import com.mtg.Motugame.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByLoginId(String id);
}
