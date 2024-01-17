package com.grp9.multinotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class notes extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private Button homeButtonNotes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        homeButtonNotes =  (Button) findViewById(R.id.homeBtn);
        homeButtonNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listvView1);

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("com.grp9.multinotes", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes.add("Make your first note");
            notes.add("Make your first note");
            notes.add("Make your first note");
            notes.add("Make your first note");
        }   else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        if (arrayAdapter.getCount() > 5){
            View item = arrayAdapter.getView(0, null, listView);
            item.measure(0,0);
            ViewGroup.LayoutParams params = new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));

        }

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EditNote.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int selectedItem = i;

                new AlertDialog.Builder(notes.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note?")
                        .setMessage("Do you want to remove this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        notes.remove(selectedItem);
                                        arrayAdapter.notifyDataSetChanged();

                                        SharedPreferences sharedPreferences = getApplicationContext()
                                                .getSharedPreferences("com.grp9.multinotes", Context.MODE_PRIVATE);

                                        HashSet<String> set = new HashSet(com.grp9.multinotes.notes.notes);
                                        sharedPreferences.edit().putStringSet("notes", set).apply();
                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }

    public void openActivity(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

}