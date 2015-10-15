package com.colinfrerichs.crossref;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by colin_000 on 10/14/2015.
 */
public class CrossRefDB extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "WZ97Xt3gIZK3mSBlflqx5yaFABBJbpNZP1ACvEBW", "AqqrCmSzJ1bCtoWcrvtV9wLwJFF7rc9ZguHj9B0u");


    }
}
