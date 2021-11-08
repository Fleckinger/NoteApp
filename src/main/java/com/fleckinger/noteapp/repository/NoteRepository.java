package com.fleckinger.noteapp.repository;

import com.fleckinger.noteapp.entity.note.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
        List<Note> findAllByUserId(long id);
}
