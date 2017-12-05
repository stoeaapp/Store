package com.imagine.mohamedtaha.store;

/**
 * Created by MANASATT on 03/12/17.
 */

public class ItemsStore {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String typeStore;

    public String getTypeStore() {
        return typeStore;
    }

    public void setTypeStore(String typeStore) {
        this.typeStore = typeStore;
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

    String notes;
    String createdDate;
}
