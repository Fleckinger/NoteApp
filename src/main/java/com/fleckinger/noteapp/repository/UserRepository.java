package com.fleckinger.noteapp.repository;

import com.fleckinger.noteapp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findUserByEmail(String email);
}
