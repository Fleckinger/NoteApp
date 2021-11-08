package com.fleckinger.noteapp.service.note;

import com.fleckinger.noteapp.entity.note.Note;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
public class NoteServiceImpl {


    private final NoteRepository noteRepository;

    @Autowired

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    //TODO реализовать логику заметок, от получения из БД до каких-то служебных методов. см. UserService
}
