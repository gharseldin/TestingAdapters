package com.example.copperadmin.testingadapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Copper Admin on 10/9/2014.
 */
public class ImagePagingActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<String> mUrls;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrls = (ArrayList<String>)getIntent().getSerializableExtra("urls");
        mPosition = getIntent().getIntExtra("position",0);

        //TODO substitute this with an XML alternative
        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        //TODO figure out why the XML Version doesn't work here
//        setContentView(R.layout.test_pager);
//        mViewPager = (ViewPager)findViewById(R.id.testPager);


        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {

                return ImagePagingFragment.newInstance(mUrls,i);
            }

            @Override
            public int getCount() {
                return mUrls.size();
            }
        });

        viewPager.setCurrentItem(mPosition);
    }
}
