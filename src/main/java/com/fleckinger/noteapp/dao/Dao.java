package com.fleckinger.noteapp.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines an abstract API that performs CRUD operations on objects of type T
 * @param <T>
 */
public interface Dao<T> {
    Optional<T> get(Long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(Long id);
}
