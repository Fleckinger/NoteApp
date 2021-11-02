package com.fleckinger.noteapp.data.note;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Note {
    //TODO добавить валидацию полей
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private NoteStatus status;
    private String title;
    @Lob
    @Column(length = 8192)
    private String content;
    private LocalDateTime uploadDate;


    public Note() {
        status = NoteStatus.AVAILABLE;
        uploadDate = LocalDateTime.now();
    }

    public Note(long id, String title, String content) {
        this.id = id;
        status = NoteStatus.AVAILABLE;
        this.title = title;
        this.content = content;
        uploadDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public NoteStatus getStatus() {
        return status;
    }

    public void setStatus(NoteStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && status == note.status && title.equals(note.title) && content.equals(note.content)
                && uploadDate.equals(note.uploadDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, title, content, uploadDate);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
