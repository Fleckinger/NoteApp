package com.fleckinger.noteapp.data.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;
    @Column(unique = true)
    private String email;

    private String password;
    private String confirmPassword;







}
