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
                //setVerse();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinBook.setSelection(0);
                bookUpdated();
                //setVerse();
            }
        });

//        spinChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                setVerse();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                spinChapter.setSelection(0);
//                setVerse();
//            }
//        });

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

    int chapter;
    List<Integer> chapters_bible = new ArrayList<Integer>();
    List<Integer> verses_bible;
    ArrayAdapter<Integer> verseAdapter;
    int verse;
    ArrayAdapter<Integer> chapterAdapter;

    public void bookUpdated() {
        switch (spinBook.getSelectedItem().toString()) {
            case "Genesis":
                setChapter(50, 67);
                break;
            case "Exodus":
                setChapter(40,51);
                break;
            case "Leviticus":
                setChapter(27,59);
                break;
            case "Numbers":
                setChapter(34, 89);
                break;
            case "Deuteronomy":
                setChapter(36, 68);
                break;
            case "Joshua":
                setChapter(24, 63);
                break;
            case "Judges":
                setChapter(21, 57);
                break;
            case "Ruth":
                setChapter(4, 23);
                break;
            case "1 Samuel":
                setChapter(31, 58);
                break;
            case "2 Samuel":
                setChapter(24, 51);
                break;
            case "1 Kings":
                setChapter(22, 66);
                break;
            case "2 Kings":
                setChapter(25, 44);
                break;
            case "1 Chronicles":
                setChapter(29, 66);
                break;
            case "2 Chronicles":
                setChapter(36, 42);
                break;
            case "Ezra":
                setChapter(10, 70);
                break;
            case "Nehemiah":
                setChapter(13, 73);
                break;
            case "Esther":
                setChapter(10, 32);
                break;
            case "Job":
                setChapter(42, 41);
                break;
            case "Psalms":
                setChapter(150, 176);
                break;
            case "Proverbs":
                setChapter(31, 36);
                break;
            case "Ecclesiastes":
                setChapter(12, 29);
                break;
            case "Song of Solomon":
                setChapter(8, 17);
                break;
            case "Isaiah":
                setChapter(66, 38);
                break;
            case "Jeremiah":
                setChapter(52, 64);
                break;
            case "Lamentations":
                setChapter(5, 66);
                break;
            case "Ezekiel":
                setChapter(48, 63);
                break;
            case "Daniel":
                setChapter(12, 49);
                break;
            case "Hosea":
                setChapter(14, 23);
                break;
            case "Joel":
                setChapter(3, 32);
                break;
            case "Amos":
                setChapter(9, 27);
                break;
            case "Obadiah":
                setChapter(1, 21);
                break;
            case "Jonah":
                setChapter(4, 17);
                break;
            case "Micah":
                setChapter(7, 20);
                break;
            case "Nahum":
                setChapter(3, 19);
                break;
            case "Habakkuk":
                setChapter(3, 20);
                break;
            case "Zephaniah":
                setChapter(3, 20);
                break;
            case "Haggai":
                setChapter(2, 23);
                break;
            case "Zechariah":
                setChapter(14, 23);
                break;
            case "Malachi":
                setChapter(4, 18);
                break;
            case "Matthew":
                setChapter(28, 75);
                break;
            case "Mark":
                setChapter(16, 72);
                break;
            case "Luke":
                setChapter(24, 81);
                break;
            case "John":
                setChapter(21, 71);
                break;
            case "Acts":
                setChapter(28, 60);
                break;
            case "Romans":
                setChapter(16, 39);
                break;
            case "1 Corinthians":
                setChapter(16, 51);
                break;
            case "2 Corinthians":
                setChapter(13,51);
                break;
            case "Galatians":
                setChapter(6, 31);
                break;
            case "Ephesians":
                setChapter(6, 33);
                break;
            case "Philippians":
                setChapter(4, 30);
                break;
            case "Colossians":
                setChapter(4, 29);
                break;
            case "1 Thessalonians":
                setChapter(5, 51);
                break;
            case "2 Thessalonians":
                setChapter(3,51);
                break;
            case "1 Timothy":
                setChapter(6,51);
                break;
            case "2 Timothy":
                setChapter(4, 51);
                break;
            case "Titus":
                setChapter(3, 16);
                break;
            case "Philemon":
                setChapter(1, 25);
                break;
            case "Hebrews":
                setChapter(13, 40);
                break;
            case "James":
                setChapter(5, 27);
                break;
            case "1 Peter":
                setChapter(5);
                break;
            case "2 Peter":
                setChapter(3);
                break;
            case "1 John":
                setChapter(5);
                break;
            case "2 John":
                setChapter(1, 13);
                break;
            case "3 John":
                setChapter(1, 14);
                break;
            case "Jude":
                setChapter(1, 25);
                break;
            case "Revelation":
                setChapter(22, 29);
                break;
        }

    }

//// TODO: 12/8/2015 create database needed for set verse
    private void setChapter(int numChapter) {
        chapters_bible = new ArrayList<>();
        chapter = 1;
        for (int i = 0; i < numChapter; i++) {
            chapters_bible.add(chapter);
            chapter++;
        }

        chapterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, chapters_bible);
        spinChapter.setAdapter(chapterAdapter);


        //deprecate if setVerse is implemented
        verses_bible = new ArrayList<>();
        verse = 1;
        for (int i = 0; i < 31; i++) {
            verses_bible.add(verse);
            verse++;
        }

        verseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, verses_bible);
        spinVerse.setAdapter(verseAdapter);
    }

    /**
     * sets a maxverse based on highest verse in the book
     * @param numChapter
     * @param maxVerse
     */
    private void setChapter(int numChapter,int maxVerse) {
        chapters_bible = new ArrayList<>();
        chapter = 1;
        for (int i = 0; i < numChapter; i++) {
            chapters_bible.add(chapter);
            chapter++;
        }

        chapterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, chapters_bible);
        spinChapter.setAdapter(chapterAdapter);



        verses_bible = new ArrayList<>();
        verse = 1;
        for (int i = 0; i < maxVerse; i++) {
            verses_bible.add(verse);
            verse++;
        }

        verseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, verses_bible);
        spinVerse.setAdapter(verseAdapter);
    }

    /**
     * You cannot create a set verse function for this unless we
     * add a SQLite database containing all limits
     * @param book
     * @param chapter
     */
    /*
    private void setVerse(){
        String book = spinBook.getSelectedItem().toString();
        //if you can make this int do that
        String chapter = spinChapter.getSelectedItem().toString();
           //use these to query database and get maxVerse


        verses_bible = new ArrayList<>();
        verse = 1;
        for (int i = 0; i < maxVerse; i++) {
            verses_bible.add(verse);
            verse++;
        }
           //set spinVerse = verses_bible
    }
    */

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

    @Override
    public void onBackPressed() {

    }
}