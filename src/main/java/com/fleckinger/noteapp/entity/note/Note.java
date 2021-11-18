package com.fleckinger.noteapp.entity.note;

import java.time.LocalDateTime;
import java.util.Objects;


public class Note {
    private long id;
    private NoteStatus status;
    private String title;
    private String content;
    private LocalDateTime uploadDate;
    private Long userId;


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

    public void setId(long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
