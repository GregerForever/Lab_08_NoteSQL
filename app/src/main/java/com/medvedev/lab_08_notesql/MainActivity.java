package com.medvedev.lab_08_notesql;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


//Lab_08_NoteSQL
//Автор: Медведев Алексей 393 группа
public class MainActivity extends AppCompatActivity {

    ListView listctl;
    ArrayList<note> list = new ArrayList<> ();
    ArrayAdapter<note> adp;

    Context context;

    //Обновление списка заметок
    void updateList()
    {
        list.clear();
        g.notes.getAllNotes(list);
        adp.notifyDataSetChanged();
    }

    //Изменение после работы дополнительного окна
    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data)
    {
        updateList();
        super.onActivityResult(reqCode, resCode, data);
    }

    //Действие при запуске приложения
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        g.notes = new DB(this, "notes.db", null, 1);

        listctl = findViewById(R.id.listNotes);
        adp = new ArrayAdapter<note>(this, android.R.layout.simple_list_item_1, list);
        listctl.setAdapter(adp);
        listctl.setOnItemClickListener((parent, view, position, id) -> {
            note n = adp.getItem(position);
            Intent i = new Intent(context, MainActivity2.class);
            i.putExtra("note-id", n.id);
            i.putExtra("note-txt", n.text);
            startActivityForResult(i, 1);
        });

        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Кнопка создания заметки
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.newItem: {
                int nid = g.notes.getMaxID() + 1;
                g.notes.addNote(nid, "Hello, World!");
                updateList();
                return  true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}