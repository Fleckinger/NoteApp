package com.fleckinger.noteapp.controller.note;

import com.fleckinger.noteapp.entity.note.Note;
import com.fleckinger.noteapp.service.note.NoteService;
import com.fleckinger.noteapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller()
@RequestMapping("/note")
public class NotesController {

    NoteService noteService;
    UserService userService;

    @Autowired
    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "noteManage/newNote";
    }

    @PostMapping("/new")
    public String newNoteSubmit(Note note) {
        note.setUser(userService.getCurrentUser());
        noteService.save(note);
        return "redirect:/note/all";
    }

    //TODO Заменить pathVariable на передачу id в теле запроса см. https://stackoverflow.com/questions/16258426/spring-mvc-hiding-url-parameters-on-get/16278367
    @GetMapping("/edit/{id}")
    public String editNoteForm(@PathVariable long id, Model model) {
        model.addAttribute("note", noteService.get(id));
        return "noteManage/editNote";
    }

    @PostMapping("/edit")
    public String editNoteSubmit(Note note) {
        //TODO через тег hidden я подтягиваю id заметки с фронта и она сохраняется, однако, через код страницы можно
        //вписать любой id и отредактировать любую заметку, даже которая не принадлежит юзеру
        note.setUser(userService.getCurrentUser());
        noteService.update(note);
        return "redirect:/note/all";
    }

    //TODO переделать на deleteMapping?
    //TODO Заменить pathVariable на передачу id в теле запроса см. https://stackoverflow.com/questions/16258426/spring-mvc-hiding-url-parameters-on-get/16278367
    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable long id) {
        noteService.delete(id);
        return "redirect:/note/all";
    }
    //TODO проблема с доступом через pathvariable в том, что можно указать вручную любой id и сделать что угодно с любой заметкой
    //нужно добавить какую-то проверку что заметка принадлежит текущему юзеру, или переместить id в тело запроса, или даже в хедер, в котором пароли обычно идут
    @PostMapping("/archive/{id}")
    public String archiveNote(@PathVariable long id) {
        noteService.archive(id);
        return "redirect:/note/all";
    }

    @GetMapping("/all")
    public String allNotes(Model model) {
        List<Note> allNotes = noteService.getAllAvailable(userService.getCurrentUserId());
        if (allNotes.isEmpty()) {
            return "noteManage/youHaveNoNotes";
        }

        model.addAttribute("allNotes", allNotes);
        return "noteManage/allAvailableNotes";
    }

    @GetMapping("archived")
    public String allArchivedNotes(Model model) {
        List<Note> allArchivedNotes = noteService.getAllArchived(userService.getCurrentUserId());
        if (allArchivedNotes.isEmpty()) {
            return "noteManage/youHaveNoArchivedNotes";
        }
        model.addAttribute("allArchivedNotes", allArchivedNotes);
        return "noteManage/allArchivedNotes";
    }
}
