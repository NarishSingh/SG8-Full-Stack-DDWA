/*
Generic interfaces
 */
package com.sg.classroster.daos;

import java.util.List;

/**
 *
 * @author naris
 * @param <T> generic object
 */
public interface Dao<T> {

    public T create(T object);

    public T readByID(int id);

    public List<T> readAll();

    public T update(T object);

    public void delete(int id);
}
