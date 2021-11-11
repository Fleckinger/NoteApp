package com.fleckinger.noteapp.service.note;

import com.fleckinger.noteapp.entity.note.Note;

import com.fleckinger.noteapp.entity.note.NoteStatus;
import com.fleckinger.noteapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void update(long id, NoteStatus status, String content) {
        Note note = get(id);
        note.setStatus(status);
        note.setContent(content);
        note.setUploadDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    public void updateStatus(long id, NoteStatus status) {
        Note note = get(id);
        note.setStatus(status);
        note.setUploadDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    public void updateContent(long id, String content) {
        Note note = get(id);
        note.setContent(content);
        note.setUploadDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    public void delete(long id) {
        noteRepository.deleteById(id);
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
