package com.example.copperadmin.testingadapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public abstract class SingleFragmentActivity extends ActionBarActivity {

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);


        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment==null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();

        }
    }
}
