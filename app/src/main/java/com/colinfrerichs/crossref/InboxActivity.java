package com.colinfrerichs.crossref;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;



public class InboxActivity extends AppCompatActivity {

    TextView message;
    Button btnDismiss;


    private ArrayList<String> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        message = (TextView) findViewById(R.id.txtMessage);
        btnDismiss = (Button) findViewById(R.id.btnDismiss);
        message.setText("No verses have been received.");
        btnDismiss.setText("Return to Send Reference");

        try{
            message.setText(ParseQuery.getQuery("BibleVerse").whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername()).getFirst().getString("verse"));
            if(!message.getText().equals("No verses have been received.")){
                btnDismiss.setText("Dismiss");

            }
        }
        catch(ParseException e){}

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessage();
                finish();
            }
        });
    }

    public /*ArrayList<String> */ String getMessages(){
        messages = new ArrayList<String>();

        try{
            ParseQuery<ParseObject> parseVerses = ParseQuery.getQuery("BibleVerse");
            parseVerses.whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername());
            parseVerses.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, com.parse.ParseException e) {
                    for (int i = 0; i < list.size(); i++) {
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

    public void deleteMessage() {
        if(!message.getText().equals("No verses have been received.")){
            try{
                ParseQuery<ParseObject> parseVerses = ParseQuery.getQuery("BibleVerse");
                parseVerses.whereEqualTo("receivingUser", ParseUser.getCurrentUser().getUsername());
                parseVerses.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        try {
                            ParseObject.deleteAll(list);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

            }
            catch(NullPointerException e) { e.printStackTrace(); }
        }

        else{
            finish();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
