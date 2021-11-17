package com.fleckinger.noteapp.dao;

import com.fleckinger.noteapp.entity.user.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements Dao<User> {

    private static final String URL = "jdbc:mysql://localhost:3306/note";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    public Optional<User> get(Long id) {
        User user = null;

        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id = ?")
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                users.add(user);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return users;
    }

    @Override
    public void save(User user) {
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)")
        ) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole());

            statement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user " +
                                "SET id = ?, first_name = ?, last_name = ?, email = ?, password = ?, role = ? " +
                                "WHERE id = ?")
        ) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole());
            statement.setLong(7, user.getId());

            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        User user = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?")

        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

}