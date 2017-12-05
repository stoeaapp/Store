package com.imagine.mohamedtaha.store;

/**
 * Created by MANASATT on 03/12/17.
 */

public class ItemStokeHouse {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirst_balanse() {
        return first_balanse;
    }

    public void setFirst_balanse(int first_balanse) {
        this.first_balanse = first_balanse;
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
    int first_balanse;
    long id_code_category;

    public long getId_code_category() {
        return id_code_category;
    }

    public void setId_code_category(long id_code_category) {
        this.id_code_category = id_code_category;
    }

    public long getId_code_store() {
        return id_code_store;
    }

    public void setId_code_store(long id_code_store) {
        this.id_code_store = id_code_store;
    }

    long id_code_store;
    String notes,createdDate;
}
