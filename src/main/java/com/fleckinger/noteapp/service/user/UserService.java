package com.fleckinger.noteapp.service.user;

import com.fleckinger.noteapp.dao.UserDao;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (user == null) {
            throw new NullPointerException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userDao.save(user);
    }

    public User findUserByEmail(String email) {
        return userDao
                .getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + "not found"));
    }

    public long getCurrentUserId() {
        return userDetailService.getCurrentAuthenticatedUser().getId();
    }

    public User getCurrentUser() {
        return userDetailService.getCurrentAuthenticatedUser();
    }

}
