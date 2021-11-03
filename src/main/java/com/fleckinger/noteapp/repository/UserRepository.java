package com.fleckinger.noteapp.repository;

import com.fleckinger.noteapp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
