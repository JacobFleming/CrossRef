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

import com.parse.ParseObject;
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

        //Creating the spinners that will choose the reference
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

        //Button will save the current reference, look up the verse in the API, and send it to Parse
        Button btnSendRef = (Button) findViewById(R.id.btnSendReference);
        btnSendRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookText = spinBook.getSelectedItem().toString();
                String chapText = spinChapter.getSelectedItem().toString();
                String verseText = spinVerse.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, bookText + " " + chapText + ":" + verseText, Toast.LENGTH_LONG).show();

                //Code to hook up API to collect verse from reference
                String bibleVerse = bookText + " " + chapText + ":" + verseText; //Empty quotes to be replaced with API call

                //Code to store verse in Parse prior to sending to random user
                SendReference referenceSender = new SendReference();
                referenceSender.sendReference(bibleVerse);
            }
        });

        Button btnInbox = (Button) findViewById(R.id.btnInbox);
        btnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InboxActivity.class));
            }
        });
    }

}
