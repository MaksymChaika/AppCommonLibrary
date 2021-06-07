package com.massuta.appcommonlibrary.db.common;

import android.database.Cursor;
import java.util.List;

public interface EntityDAO<T extends BaseEntity> {

    List<T> getAll();

    T get(int id);

    void delete(T entity);

    void deleteAll();

    int create(T entity);

    void update(T entity);

    Cursor getCursor();
}
