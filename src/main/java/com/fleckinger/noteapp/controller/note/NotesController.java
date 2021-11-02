package com.fleckinger.noteapp.controller.note;

import com.fleckinger.noteapp.data.note.Note;
import com.fleckinger.noteapp.data.note.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


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
    public ModelAndView newNoteSubmit(@ModelAttribute Note note, Model model) {
        model.getAttribute("note");
        noteRepository.save(note);
        return new ModelAndView("redirect:/note/all");
    }


    @GetMapping("/{id}")
    public String getById(@RequestParam long id, Model model) {
        Note note = noteRepository.findById(id).orElseThrow(); //TODO сделать выброс ошибки
        //TODO если заметка найдена в базе, собрать ее и отправить на фронт
        return "";
    }


    @GetMapping("/all")
    public String allNotes(Model model) {
        List<Note> allNotes = new ArrayList<>();
        noteRepository.findAll().forEach(allNotes::add);

        if (allNotes.isEmpty()) {
            return "noteManage/youHaveNoNotes";
        }

        model.addAttribute( "allNotes", allNotes);
        return "noteManage/allAvailableNotes";
    }


    @GetMapping("archived_notes")
    public String allArchivedNotes() {
        //TODO получить все архивированные заметки для конкретного юзера
        return "noteManage/allArchivedNotes";
    }
}
