package com.medvedev.lab_08_notesql;

public class note {
    public int id;
    public String text;

    public String toString()
    {
        return String.valueOf(id) + " " + text;
    }
}
