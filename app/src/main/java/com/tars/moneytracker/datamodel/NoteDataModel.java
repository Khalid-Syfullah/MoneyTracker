package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoteDataModel {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("note")
    @Expose
    private String note;

    public NoteDataModel(String email, String note) {
        this.email = email;
        this.note = note;
    }
    public NoteDataModel(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }
}
