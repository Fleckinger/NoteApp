package com.fleckinger.noteapp.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines an abstract API that performs CRUD operations
 * @param <T>
 */
public interface Dao<T> {

    /**
     * @param id
     * @return record from DB by uniq id
     */
    Optional<T> get(Long id);

    /**
     * @return All records from DB as List
     */
    List<T> getAll();

    /**
     * Saves passed as a parameter entity to the DB
     * @param t
     */
    void save(T t);

    /**
     * Updates entity in the DB
     * @param t
     */
    void update(T t);

    /**
     * Deletes entity from the DB by id
     * @param id
     */
    void delete(Long id);
}
