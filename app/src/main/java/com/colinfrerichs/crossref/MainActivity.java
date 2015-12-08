package com.colinfrerichs.crossref;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinBook;
    private Spinner spinChapter;
    private Spinner spinVerse;
    private String bibleURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bibleURL = new String();
        //Creating the spinners that will choose the reference
        spinBook = (Spinner) findViewById(R.id.spinBook);
        spinChapter = (Spinner) findViewById(R.id.spinChapter);
        spinVerse = (Spinner) findViewById(R.id.spinVerse);


        ArrayAdapter<CharSequence> bookAdapter = ArrayAdapter.createFromResource(this,
                R.array.books_bible, android.R.layout.simple_spinner_item);
        spinBook.setAdapter(bookAdapter);


        List<Integer> chapters_bible = new ArrayList<Integer>();
        int chapter = 1;
        for (int i = 0; i < 150; i++) {
            chapters_bible.add(chapter);
            chapter++;
        }

        ArrayAdapter<Integer> chapterAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, chapters_bible);
        spinChapter.setAdapter(chapterAdapter);

        List<Integer> verses_bible = new ArrayList<Integer>();
        int verse = 1;
        for (int i = 0; i < 176; i++) {
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
                final String bookText = spinBook.getSelectedItem().toString();
                final String chapText = spinChapter.getSelectedItem().toString();
                final String verseText = spinVerse.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, bookText + " " + chapText + ":" + verseText, Toast.LENGTH_LONG).show();

                //reset book text if 1 Samuel, 2 Samuel, ect.

                bibleURL = "https://www.biblegateway.com/passage/?search=" + bookText + "+" + chapText + "%3A" + verseText + "&version=ESV";


//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(bibleURL)
//                        .build();
//
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(final Response response) throws IOException {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG);
//                            }
//                        });


                //Code to hook up API to collect verse from reference
                String bibleVerse = bookText + " " + chapText + ":" + verseText;
                //     System.out.print(bibleVerse);
                //Code to store verse in Parse prior to sending to random user
                SendReference referenceSender = new SendReference();
                referenceSender.sendReference(bibleVerse);
            }
//                });
//            }
        });
        spinBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookUpdated();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinBook.setSelection(0);
                bookUpdated();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ParseUser.logOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please use logout in toolbar.", Toast.LENGTH_LONG).show();
    }

    int chapter;
    List<Integer> chapters_bible = new ArrayList<Integer>();
    List<Integer> verses_bible;
    ArrayAdapter<Integer> verseAdapter;
    int verse;
    ArrayAdapter<Integer> chapterAdapter;

    public void bookUpdated() {
        switch (spinBook.getSelectedItem().toString()) {
            case "Genesis":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;

            //// TODO: 12/7/2015
            case "Exodus":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 40; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Leviticus":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 27; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Numbers":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 36; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Deuteronomy":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 34; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Joshua":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 24; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Judges":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 21; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Ruth":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 4; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Samuel":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 31; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Samuel":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 24; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Kings":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 22; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Kings":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 25; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Chronicles":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 29; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Chronicles":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 36; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            //TODO
            case "Ezra":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Nehemiah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Esther":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Job":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Psalms":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Proverbs":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Ecclesiastes":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Song of Solomon":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Isaiah":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Jeremiah":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Lamentations":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Ezekiel":
                chapters_bible = new ArrayList<>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Daniel":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Hosea":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Joel":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Amos":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Obadiah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Jonah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Micah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Nahum":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Habakkuk":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Zephaniah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Haggai":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Zechariah":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Malachi":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Matthew":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Mark":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Luke":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "John":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Acts":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Romans":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Corinthians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Corinthians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Galatians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Ephesians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Philippians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Colossians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Thessalonians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Thessalonians":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "1 Timothy":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "2 Timothy":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Titus":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Philemon":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "Hebrews":
                chapters_bible = new ArrayList<Integer>();
                chapter = 1;
                for (int i = 0; i < 50; i++) {
                    chapters_bible.add(chapter);
                    chapter++;
                }

                chapterAdapter = new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, chapters_bible);
                spinChapter.setAdapter(chapterAdapter);

                verses_bible = new ArrayList<Integer>();
                verse = 1;
                for (int i = 0; i < 31; i++) {
                    verses_bible.add(verse);
                    verse++;
                }

                verseAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, verses_bible);
                spinVerse.setAdapter(verseAdapter);
                break;
            case "James":
                setChapter(80);
                break;
            case "1 Peter":
                setChapter(80);
                break;
            case "2 Peter":
                setChapter(80);
                break;
            case "1 John":
                setChapter(80);
                break;
            case "2 John":
                setChapter(80);
                break;
            case "3 John":
                setChapter(80);
                break;
            case "Jude":
                setChapter(80);
                break;
            case "Revelation":
                setChapter(80);
                break;

        }

    }

    public void setChapter(int numChapter) {
        chapters_bible = new ArrayList<Integer>();
        chapter = 1;
        for (int i = 0; i < numChapter; i++) {
            chapters_bible.add(chapter);
            chapter++;
        }

        chapterAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, chapters_bible);
        spinChapter.setAdapter(chapterAdapter);

        verses_bible = new ArrayList<Integer>();
        verse = 1;
        for (int i = 0; i < 31; i++) {
            verses_bible.add(verse);
            verse++;
        }

        verseAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, verses_bible);
        spinVerse.setAdapter(verseAdapter);
    }
}
//}
//        if(spinBook.getSelectedItem().toString().equals("Genesis")){
//            List<Integer> chapters_bible = new ArrayList<Integer>();
//            int chapter = 1;
//            for(int i = 0; i < 50; i++){
//                chapters_bible.add(chapter);
//                chapter++;
//            }
//
//            ArrayAdapter<Integer> chapterAdapter = new ArrayAdapter<Integer>(this,
//                    android.R.layout.simple_spinner_item, chapters_bible);
//            spinChapter.setAdapter(chapterAdapter);
//
//            List<Integer> verses_bible = new ArrayList<Integer>();
//            int verse = 1;
//            for(int i = 0; i < 31; i++){
//                verses_bible.add(verse);
//                verse++;
//            }
//
//            ArrayAdapter<Integer> verseAdapter = new ArrayAdapter<Integer>(this,
//                    android.R.layout.simple_spinner_item, verses_bible);
//            spinVerse.setAdapter(verseAdapter);
//        }
//
//        else if(spinBook.getSelectedItem().toString().equals("Exodus")){
//
//        }


