package com.colinfrerichs.crossref;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView messageList = (ListView) findViewById(R.id.messageList);

        List<String> messages = getMessages();

        ArrayAdapter<String> messagesAdapter = new ArrayAdapter<String>(this, R.layout.list_item, messages);

        messageList.setAdapter(messagesAdapter);

    }

    public List<String> getMessages(){
        final List<String> messages = new ArrayList<String>();

        try{
            ParseQuery<ParseObject> parseVerses = ParseQuery.getQuery("BibleVerse");
            parseVerses.whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername());
            parseVerses.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, com.parse.ParseException e) {
                    for(int i = 0; i < list.size(); i++){
                        messages.add(list.get(i).get("verse").toString());
                        System.out.print(messages.get(i));
                    }
                }
            });

        }
        catch(NullPointerException e) {}


        return messages;
    }
}
