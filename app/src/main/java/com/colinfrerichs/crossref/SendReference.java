package com.colinfrerichs.crossref;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Owner on 11/19/2015.
 */
public class SendReference {

    public SendReference(){}

    ArrayList<ParseUser> mParseUsers;
    String receivingUser;

    public void sendReference(String verse){

        mParseUsers = LoginActivity.parseUsers;


      //  System.out.print("this is size" + mParseUsers.size());

        //Code to find random user
        Random random = new Random();
        int i = random.nextInt(mParseUsers.size());
        receivingUser = mParseUsers.get(i).getUsername();


        //Code to store verse in Parse
        ParseObject message = new ParseObject("BibleVerse");

        message.put("verse", verse);
        message.put("receivingUser", receivingUser);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
        //Code to send reference to random user

        //Code to delete reference

    }


}
