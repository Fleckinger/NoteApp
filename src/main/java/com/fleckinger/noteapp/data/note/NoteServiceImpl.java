package com.fleckinger.noteapp.data.note;

import com.fleckinger.noteapp.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public long delete(Note note) {
        noteRepository.delete(note);
        return note.getId(); //TODO разобраться зачем тут возвращаю id
    }

    @Override
    public long delete(long id) {
        noteRepository.deleteById(id);
        return id;
    }

    @Override
    public Optional<Note> get(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Can't find note with id less than 1");
        }
        return noteRepository.findById(id);
    }

    @Override
    public void update(Note note) {
        if (note == null) {
            throw new IllegalArgumentException();
        }

        if (note.getId() < 1) {
            throw new IllegalArgumentException("Can't update note with id less than 1");
        }

        noteRepository.save(note);
    }

    @Override
    public List<Note> getAllAvailableNotes(User user) {

        return null;
    }

    @Override
    public List<Note> getAllAvailableNotesForPeriod(User user, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public List<Note> getAllArchivedNotes(User user) {
        return null;
    }

    @Override
    public List<Note> getAllArchivedNotesForPeriod(User user, LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
