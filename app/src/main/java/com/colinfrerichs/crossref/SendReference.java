package com.colinfrerichs.crossref;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 11/19/2015.
 */
public class SendReference {

    public SendReference(){}

    public void sendReference(String verse){

        final ArrayList<ParseUser> parseUsers = new ArrayList<ParseUser>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for(int i = 0; i < objects.size(); i++)
                    parseUsers.add(i, objects.get(i));
                } else {
                    // Something went wrong.
                }
            }
        });


        //Code to store verse in Parse
        ParseObject.create("BibleVerse").add("verse", verse);

        //Code to find random user

        //Code to send reference to random user

        //Code to delete reference

    }
}
