package com.fleckinger.noteapp.controller.note;

import com.fleckinger.noteapp.data.note.Note;
import com.fleckinger.noteapp.data.note.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller()
@RequestMapping("/note")
public class NotesController {

    NoteRepository noteRepository;

    @Autowired
    public NotesController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @GetMapping("/new")
    public String newNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "noteManage/newNote";
    }


    @PostMapping("/new")
    public String newNoteSubmit(@ModelAttribute Note note, Model model) {
        model.getAttribute("note");
        noteRepository.save(note);
        return "noteManage/newNote";
        //TODO после создания новой записи, переходить на страницу с всеми заметками
    }


    @GetMapping("/{id}")
    public String getById(@RequestParam long id, Model model) {
        Note note = noteRepository.findById(id).orElseThrow(); //TODO сделать выброс ошибки
        //TODO если заметка найдена в базе, собрать ее и отправить на фронт
        return "";
    }


    @GetMapping("/all")
    public String allNotes(Model model) {
        //TODO получить все заметки для конкретного юзера
        return "noteManage/allAvailableNotes";
    }


    @GetMapping("archived_notes")
    public String allArchivedNotes() {
        //TODO получить все архивированные заметки для конкретного юзера
        return "noteManage/allArchivedNotes";
    }
}
