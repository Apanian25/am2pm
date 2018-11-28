package com.dawson.jonat.stockers.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "note_table")
public class Note {


    @NonNull
    @ColumnInfo(name = "note")
    private String note;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public Note(@NonNull String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public int getId() {
        return this.id;
    }
}
