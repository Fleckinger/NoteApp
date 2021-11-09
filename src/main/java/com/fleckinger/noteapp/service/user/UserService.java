package com.fleckinger.noteapp.service.user;

import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        repository.save(user);
    }

    public User findUserByEmail(String email) {
        return repository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + "not found"));
    }

}
