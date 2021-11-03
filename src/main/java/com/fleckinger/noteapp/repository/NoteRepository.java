package com.fleckinger.noteapp.repository;

import com.fleckinger.noteapp.entity.note.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

}
