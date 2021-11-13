package com.fleckinger.noteapp.service.note;

import com.fleckinger.noteapp.dao.NoteDao;
import com.fleckinger.noteapp.entity.note.Note;

import com.fleckinger.noteapp.entity.note.NoteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteDao noteDao;


    @Autowired
    public NoteService(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public void save(Note note) {
        noteDao.save(note);
    }

    public Note get(long id) {
        return noteDao.get(id)
                .orElseThrow(() -> new UsernameNotFoundException("Note not found"));
    }

    public List<Note> getAllAvailable(long userId) {

        return noteDao.getAllByUserIdAndStatus(userId, NoteStatus.AVAILABLE);
    }

    public List<Note> getAllArchived(long userId) {
        return noteDao.getAllByUserIdAndStatus(userId, NoteStatus.ARCHIVED);
    }

    public void update(Note note) {
        noteDao.update(note);
    }

    public void delete(long id) {
        noteDao.delete(id);
    }

    public void archive(long id) {
        Note note = get(id);
        note.setStatus(NoteStatus.ARCHIVED);
        update(note);
    }

    public void unarchive(long id) {
        Note note = get(id);
        note.setStatus(NoteStatus.AVAILABLE);
        update(note);
    }

}
