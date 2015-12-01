package com.colinfrerichs.crossref;

import com.parse.ParseObject;

/**
 * Created by Owner on 11/19/2015.
 */
public class SendReference {

    public SendReference(){}

    public void sendReference(String verse){
        //Code to store verse in Parse
        ParseObject.create("BibleVerse").add("verse", verse);

        //Code to find random user

        //Code to send reference to random user

        //Code to delete reference

    }
}
