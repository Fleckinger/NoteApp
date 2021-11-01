package com.fleckinger.noteapp.data.note;

import com.fleckinger.noteapp.data.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoteService {

    void save(Note note);

    long delete(Note note);

    long delete(long id);

    Optional<Note> get(long id);

    void update(Note note);

    List<Note> getAllAvailableNotes(User user);

    List<Note> getAllAvailableNotesForPeriod(User user, LocalDateTime from, LocalDateTime to);

    List<Note> getAllArchivedNotes(User user);

    List<Note> getAllArchivedNotesForPeriod(User user, LocalDateTime from, LocalDateTime to);

}
