package com.massuta.appcommonlibrary.db.common;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.massuta.appcommonlibrary.db.AppDatabase;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityDAO<T  extends BaseEntity> implements EntityDAO<T>{

    protected AppDatabase appDatabase;

    protected String TABLE_NAME;

    public BaseEntityDAO(AppDatabase appDatabase){

        this.appDatabase    = appDatabase;
        TABLE_NAME          = getEntityTable();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        db.execSQL("delete from "+TABLE_NAME);
        db.close();
    }

    @Override
    public void delete(T entity) {
        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        db.delete(TABLE_NAME,"id=?",new String[] {String.valueOf(entity.getId())});
        db.close();
    }

    @Override
    public void update(T entity) {
        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        ContentValues contentValues = entityToContentValues(entity);
        db.update(TABLE_NAME,contentValues,"id=?",new String[] {String.valueOf(entity.getId())});
        db.close();
    }

    @Override
    public T get(int id) {
        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        String[] field      = getEntityFields();
        Cursor c = db.query(TABLE_NAME,field, "id=?", new String[] {String.valueOf(id)}, null, null, null);

        if (c.moveToFirst())
        {
            return  createEntityFromCursor(c);
        }
        c.close();
        db.close();

        return null;
    }

    @Override
    public int create(T entity) {
        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        ContentValues contentValues = entityToContentValues(entity);
        int result = (int) db.insert(TABLE_NAME, null, contentValues);
        entity.setId(result);
        db.close();
        return result;
    }

    @Override
    public List<T> getAll() {

        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        String[] field      = getEntityFields();

        Cursor c = db.query(TABLE_NAME, field, null, null, null, null, null);

        ArrayList<T> result = new ArrayList<T>(c.getCount());

        if (c.moveToFirst())
        {
            do {
                T entity    = createEntityFromCursor(c);
                result.add(entity);
            }while(c.moveToNext());
        }
        c.close();

        db.close();

        return result;
    }

    @Override
    public Cursor getCursor() {

        SQLiteDatabase db   = appDatabase.getSQLiteDatabase();
        String[] field      = getEntityFields();

        Cursor c = db.query(TABLE_NAME, field, null, null, null, null, null);

        return c;
    }

    protected abstract T createEntityFromCursor(Cursor c);

    protected abstract ContentValues entityToContentValues(T entity);

    protected abstract String[] getEntityFields();

    protected abstract String getEntityTable();
}
