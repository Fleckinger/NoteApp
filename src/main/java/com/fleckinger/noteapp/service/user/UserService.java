package com.fleckinger.noteapp.service.user;

import com.fleckinger.noteapp.dao.UserDao;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, UserDetailServiceImpl userDetailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao
                .getUserByEmail(email);
    }

    public long getCurrentUserId() {
        return userDetailService.getCurrentAuthenticatedUser().getId();
    }

    public User getCurrentUser() {
        return userDetailService.getCurrentAuthenticatedUser();
    }

    public boolean userExists(String email) {
        return findUserByEmail(email).isPresent();
    }

}
