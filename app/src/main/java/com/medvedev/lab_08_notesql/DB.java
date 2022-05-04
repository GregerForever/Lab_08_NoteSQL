package com.medvedev.lab_08_notesql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Создание таблицы заметок
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT, text TEXT)";
        db.execSQL(sql);
    }

    //Получение максимального ID таблицы
    public int getMaxID()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst())
        {
            return cur.getInt(0);
        }
        return 0;
    }

    //Добавление заметки
    public void addNote(int id, String sText)
    {
        String sId = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES (" + sId + ", '" + sText + "');";
        db.execSQL(sql);
    }

    //чтение заметки
    public String getNote(int id)
    {
        String sId = String.valueOf(id);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT text FROM notes WHERE id = " + sId + ";";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst() == true)
        {
            return c.getString(0);
        }
        return "";
    }

    //Изменение заметки
    public void alterNote(int id, String sText)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE notes SET text = '" + sText + "' WHERE id = '" + id + "';";
        db.execSQL(sql);
    }

    //Удаление заметки
    public void deleteNote(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM notes WHERE id = '" + id + "';";
        db.execSQL(sql);
    }

    //Получение всех заметок
    public void getAllNotes(ArrayList<note> list)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, text FROM notes";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst() == true)
        {
            do {
                note n = new note();
                n.id = c.getInt(0);
                n.text = c.getString(1);
                list.add(n);
            } while (c.moveToNext() == true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
