package com.fleckinger.noteapp.controller.note;

import com.fleckinger.noteapp.entity.note.Note;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.security.UserDetailServiceImpl;
import com.fleckinger.noteapp.service.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller()
@RequestMapping("/note")
public class NotesController {

    NoteService noteService;


    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/new")
    public String newNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "noteManage/newNote";
    }


    @PostMapping("/new")
    public ModelAndView newNoteSubmit(@ModelAttribute Note note, Model model) {
        model.getAttribute("note");
        UserDetailServiceImpl userService = new UserDetailServiceImpl();
        User user = userService.getCurrentAuthenticatedUser();
        note.setUser(user);
        noteService.save(note);
        return new ModelAndView("redirect:/note/all");
    }


    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable long id) {
        noteService.delete(id);
        return "redirect:/note/all";
    }


    @GetMapping("/{id}") //TODO а нужно ли вообще давать доступ по конкретному id?
    public String getById(@PathVariable long id, Model model) {
        Note note = noteService.get(id); //TODO если заметки нет - выбросить ошибку
        model.addAttribute(note);
        return "noteManage/singleNote";
    }


    @GetMapping("/all")
    public String allNotes(Model model) {
        List<Note> allNotes = noteService.getAllAvailable(noteService.getCurrentUserId()); //TODO возможно переделать метод гетЮзерИд
        // Так как юзер заправшивает данные на основании сессии
        if (allNotes.isEmpty()) {
            return "noteManage/youHaveNoNotes";
        }

        model.addAttribute("allNotes", allNotes);
        return "noteManage/allAvailableNotes";
    }


    @GetMapping("archived_notes")
    //TODO изменить ридерект если нет архивных заметок, с  надписью что архив пуст
    // и ссылкой на все доступные заметки
    //TODO на страницу с архивными заметками добавить кнопки вытащить из архива, удалить
    public String allArchivedNotes(Model model) {
        List<Note> allArchivedNotes = noteService.getAllArchived(noteService.getCurrentUserId());
        if (allArchivedNotes.isEmpty()) {
            return "noteManage/youHaveNoNotes"; // или добавить страницу которая пишет что нет архивированных заметок
            // или динамически менять страницу
        }
        model.addAttribute("allArchivedNotes", allArchivedNotes);
        return "noteManage/allArchivedNotes";
    }
}
