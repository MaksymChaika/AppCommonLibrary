package com.massuta.appcommonlibrary.db.common;

public abstract class BaseEntity {

    protected int _id;

    public BaseEntity(int id) {
        this._id = id;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }


}