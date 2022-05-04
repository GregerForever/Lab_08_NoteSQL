package com.medvedev.lab_08_notesql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    EditText textctl;
    int nId;
    String nText;

    ArrayList<note> list = new ArrayList<> ();
    ArrayAdapter<note> adp;

    //Диалоговое окно с удалением заметки
    public Dialog onCreateDialog(int key)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder
                .setTitle("Delete Value")
                .setIcon(R.drawable.delete)
                .setMessage("Do you want to delete value?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g.notes.deleteNote(key);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                })
                .create();
    }

    //Действие при начале работы в дополнительном окне
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textctl = findViewById(R.id.textContent);

        Intent i = getIntent();
        nId = i.getIntExtra("note-id", 0);
        nText = i.getStringExtra("note-txt");

        textctl.setText(nText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menulock, menu);
        return true;
    }

    //Изменение и удаление заметки
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        Intent i = new Intent();

        switch (id)
        {
            case R.id.LockItem:
            {
                g.notes.alterNote(nId, textctl.getText().toString());
                finish();
                return true;
            }
            case R.id.DeleteItem:
            {
                //g.notes.deleteNote(nId);
                Dialog delDialog = onCreateDialog(nId);
                delDialog.show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}