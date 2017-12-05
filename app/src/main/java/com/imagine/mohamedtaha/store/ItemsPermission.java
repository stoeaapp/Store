package com.imagine.mohamedtaha.store;

/**
 * Created by MANASATT on 03/12/17.
 */

public class ItemsPermission {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePermission() {
        return namePermission;
    }

    public void setNamePermission(String namePermission) {
        this.namePermission = namePermission;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    int id;
    String namePermission, notes,createdDate;
}
