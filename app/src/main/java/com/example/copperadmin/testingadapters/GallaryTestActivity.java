package com.example.copperadmin.testingadapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.util.Log;

public class GallaryTestActivity extends SingleFragmentActivity {

    public Fragment createFragment(){
        return new GallaryFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("TestingAdapters", "Recieved the following search query" + query);
            //Action that will happen by the fragment
            GallaryFragment fragment = GallaryFragment.newInstance("https://api.instagram.com/v1/tags/" + query + "/media/recent?&count=100&access_token=1460323222.858d928.3b9e24a3be9e4278b9cd73e120044e0a");
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }
}
