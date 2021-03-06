package com.fleckinger.noteapp.controller.note;

import com.fleckinger.noteapp.entity.note.Note;
import com.fleckinger.noteapp.entity.note.NoteStatus;
import com.fleckinger.noteapp.entity.user.User;
import com.fleckinger.noteapp.service.note.NoteService;
import com.fleckinger.noteapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/note")
public class NotesController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newNote(Model model) {
        model.addAttribute("note", new Note());
        return "noteManage/newNote";
    }

    @PostMapping("/new")
    public String saveNote(Note note) {
        note.setUserId(userService.getCurrentUser().getId());
        noteService.save(note);
        return "redirect:/note/all";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable long id, Model model) {
        if (!userHasAccessToNote(id)) {
            return "errors/notFound";
        }
        model.addAttribute("note", noteService.get(id));
        return "noteManage/editNote";
    }

    @PostMapping("/edit")
    public String updateNote(Note note) {
        if (!userHasAccessToNote(note.getId()) || note.getId() == 0) {
            return "errors/notFound";
        }
        note.setUserId(userService.getCurrentUser().getId());
        noteService.update(note);
        return "redirect:/note/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable long id) {
        if (!userHasAccessToNote(id)) {
            return "errors/notFound";
        }
        String redirectPath = "redirect:/note/all";
        if (noteService.get(id).getStatus().equals(NoteStatus.ARCHIVED)) {
            redirectPath = "redirect:/note/archived";
        }

        noteService.delete(id);
        return redirectPath;
    }

    @PostMapping("/archive/{id}")
    public String archiveNote(@PathVariable long id) {
        if (!userHasAccessToNote(id)) {
            return "errors/notFound";
        }
        noteService.archive(id);
        return "redirect:/note/all";
    }

    @PostMapping("/unarchive/{id}")
    public String unarchiveNote(@PathVariable long id) {
        if (!userHasAccessToNote(id)) {
            return "errors/notFound";
        }
        noteService.unarchive(id);
        return "redirect:/note/archived";
    }

    @GetMapping("/all")
    public String allNotes(Model model) {
        List<Note> allNotes = noteService.getAllAvailable(userService.getCurrentUserId());
        model.addAttribute("allNotes", allNotes);
        return "noteManage/allAvailableNotes";
    }

    @GetMapping("/archived")
    public String allArchivedNotes(Model model) {
        List<Note> allArchivedNotes = noteService.getAllArchived(userService.getCurrentUserId());
        model.addAttribute("allArchivedNotes", allArchivedNotes);
        return "noteManage/allArchivedNotes";
    }

    @GetMapping("/search")
    public String findNotes(@RequestParam(name = "keyword") String keyword, Model model) {
        if (keyword.isBlank()) {
            return "redirect:/note/all";
        }
        List<Note> searchResult = noteService.search(keyword);

        model.addAttribute("allNotes", searchResult);
        return "noteManage/allAvailableNotes";
    }

    private boolean userHasAccessToNote(long noteId) {
        User currentUser = userService.getCurrentUser();
        Note requestedNote = noteService.get(noteId);

        return requestedNote.getUserId().equals(currentUser.getId());
    }


}
