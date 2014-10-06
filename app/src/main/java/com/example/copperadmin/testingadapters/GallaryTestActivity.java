package com.example.copperadmin.testingadapters;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class GallaryTestActivity extends SingleFragmentActivity {

    public Fragment createFragment(){
        return new GallaryFragment();
    }
}
