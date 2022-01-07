package com.fleckinger.noteapp.dao;

import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Autowired
    private Connection connection;

    @Test
    void shouldSaveUserToDatabase() {
        //given
        User user = new User(1, "firstNameTest", "lastNameTest", "email@test.test", "test", Role.ROLE_USER);
        //when

        //then
    }
}