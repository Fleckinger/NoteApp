package com.fleckinger.noteapp.dao;

import com.fleckinger.noteapp.config.JdbcConfig;
import com.fleckinger.noteapp.dao.Dao;
import com.fleckinger.noteapp.entity.note.Note;
import com.fleckinger.noteapp.entity.note.NoteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NoteDao implements Dao<Note> {
    
    private Connection connection;
    
    @Override
    public Optional<Note> get(Long id) {
        Note note = null;

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM note WHERE id = ?")
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    note = new Note();
                    fillNote(note, resultSet);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(note);
    }

    @Override
    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM note");
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Note note = new Note();
                fillNote(note, resultSet);
                notes.add(note);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return notes;
    }

    @Override
    public void save(Note note) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO note VALUES (?, ?, ?, ?, ?, ?)")
        ) {
            fillStatement(note, statement);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void update(Note note) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "UPDATE note " +
                             "SET id = ?, status = ?, title = ?, content = ?, upload_date = ?, user_id = ? " +
                             "WHERE id = ?")
        ) {
            fillStatement(note, statement);
            statement.setLong(7, note.getId());

            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM note WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<Note> search(String keywords) {
        List<Note> notes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM note WHERE title LIKE ? OR content LIKE ?")) {

            statement.setString(1, "%" + keywords + "%");
            statement.setString(2, "%" + keywords + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    fillNote(note, resultSet);
                    notes.add(note);
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return notes;
    }

    public List<Note> getAllByUserIdAndStatus(long id, NoteStatus status) {
        List<Note> notes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement
                        ("SELECT * FROM note WHERE user_id = ? AND status = ?")
        ) {
            statement.setLong(1, id);
            statement.setString(2, status.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    fillNote(note, resultSet);
                    notes.add(note);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return notes;
    }

    private void fillNote(Note note, ResultSet resultSet) throws SQLException {
        note.setId(resultSet.getLong("id"));
        note.setStatus(NoteStatus.valueOf(resultSet.getString("status")));
        note.setTitle(resultSet.getString("title"));
        note.setContent(resultSet.getString("content"));
        note.setUploadDate(resultSet.getObject("upload_date", LocalDateTime.class));
        note.setUserId(resultSet.getLong("user_id"));
    }

    private void fillStatement(Note note, PreparedStatement statement) throws SQLException {
        statement.setLong(1, note.getId());
        statement.setString(2, note.getStatus().toString());
        statement.setString(3, note.getTitle());
        statement.setString(4, note.getContent());
        statement.setObject(5, note.getUploadDate());
        statement.setLong(6, note.getUserId());
    }

    @Autowired
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
