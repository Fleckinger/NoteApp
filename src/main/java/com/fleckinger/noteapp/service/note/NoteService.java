package com.fleckinger.noteapp.service.note;

import com.fleckinger.noteapp.entity.note.Note;

import com.fleckinger.noteapp.entity.note.NoteStatus;
import com.fleckinger.noteapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void save(Note note) {
        noteRepository.save(note);
    }

    public Note get(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Note not found"));
    }

    public List<Note> getAllAvailable(long userId) {

        return noteRepository.findAllByUserIdAndStatus(userId, NoteStatus.AVAILABLE);
    }

    public List<Note> getAllArchived(long userId) {
        return noteRepository.findAllByUserIdAndStatus(userId, NoteStatus.ARCHIVED);
    }

    public void update(Note note) {
        noteRepository.save(note);
    }

    public void delete(long id) {
        noteRepository.deleteById(id);
        //TODO переделать чтобы метод возвращал boolean? true если успешно удалилось, false если такой id не найден в базе
    }

    public void archive(long id) {
        Note note = get(id);
        note.setStatus(NoteStatus.ARCHIVED);
        save(note);
    }

    public void unarchive(long id) {
        Note note = get(id);
        note.setStatus(NoteStatus.AVAILABLE);
        save(note);
    }

}
