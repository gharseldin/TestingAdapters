package com.example.copperadmin.testingadapters;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Copper Admin on 10/9/2014.
 */
public class PictureLab {
    private static PictureLab mPictureLab;
    private Context mContext;
    private ArrayList<String> mPictureUrls;

    private PictureLab(Context context){

        mContext = context.getApplicationContext();
        mPictureUrls = new ArrayList<String>();

    }

    public static PictureLab get(Context context){
        if (mPictureLab==null){
            mPictureLab = new PictureLab(context);
        }
        return mPictureLab;
    }

    public ArrayList<String> getPictureUrls() {
        return mPictureUrls;
    }

    public void addPictureUrls(ArrayList<String> pictureUrls) {
        mPictureUrls.addAll(pictureUrls);
    }
}
