package com.colinfrerichs.crossref;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner spinBook = (Spinner) findViewById(R.id.spinBook);
        final Spinner spinChapter = (Spinner) findViewById(R.id.spinChapter);
        final Spinner spinVerse = (Spinner) findViewById(R.id.spinVerse);

        ArrayAdapter<CharSequence> bookAdapter = ArrayAdapter.createFromResource(this,
                                                 R.array.books_bible, android.R.layout.simple_spinner_item);
        spinBook.setAdapter(bookAdapter);

        List<Integer> chapters_bible = new ArrayList<Integer>();
        int chapter = 1;
        for(int i = 0; i < 150; i++){
            chapters_bible.add(chapter);
            chapter++;
        }

        ArrayAdapter<Integer> chapterAdapter = new ArrayAdapter<Integer>(this,
                                                   android.R.layout.simple_spinner_item, chapters_bible);
        spinChapter.setAdapter(chapterAdapter);

        List<Integer> verses_bible = new ArrayList<Integer>();
        int verse = 1;
        for(int i = 0; i < 176; i++){
            verses_bible.add(verse);
            verse++;
        }

        ArrayAdapter<Integer> verseAdapter = new ArrayAdapter<Integer>(this,
                                                 android.R.layout.simple_spinner_item, verses_bible);
        spinVerse.setAdapter(verseAdapter);

        Button btnSendRef = (Button) findViewById(R.id.btnSendReference);
        btnSendRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookText = spinBook.getSelectedItem().toString();
                String chapText = spinChapter.getSelectedItem().toString();
                String verseText = spinVerse.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, bookText + " " + chapText + ":" + verseText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
