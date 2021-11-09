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

    @PostMapping("/archive/{id}")
    public String archiveNote(@PathVariable long id) {
        noteService.archive(id);
        return "redirect:/note/all";
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

    @GetMapping("archived")
    public String allArchivedNotes(Model model) {
        List<Note> allArchivedNotes = noteService.getAllArchived(noteService.getCurrentUserId());
        if (allArchivedNotes.isEmpty()) {
            return "noteManage/youHaveNoArchivedNotes";
        }
        model.addAttribute("allArchivedNotes", allArchivedNotes);
        return "noteManage/allArchivedNotes";
    }
}
