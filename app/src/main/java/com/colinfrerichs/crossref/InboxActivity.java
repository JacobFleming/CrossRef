package com.colinfrerichs.crossref;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class InboxActivity extends AppCompatActivity {

    private ArrayList<String> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView message = (TextView) findViewById(R.id.txtMessage);



        try{
            message.setText(ParseQuery.getQuery("BibleVerse").whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername()).getFirst().getString("verse"));
        }
        catch(ParseException e){}

    }

    public /*ArrayList<String> */ String getMessages(){
        messages = new ArrayList<String>();

        try{
            ParseQuery<ParseObject> parseVerses = ParseQuery.getQuery("BibleVerse");
            parseVerses.whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername());
            parseVerses.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, com.parse.ParseException e) {
                    for(int i = 0; i < list.size(); i++){
                        messages.add(list.get(i).getString("verse"));
                        System.out.print(messages.get(i));
                    }
                }
            });
                    return messages.get(0);
        }
        catch(NullPointerException e) {}
return null;

       // return messages;
    }
}
