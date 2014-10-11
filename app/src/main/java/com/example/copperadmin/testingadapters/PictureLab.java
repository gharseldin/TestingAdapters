package com.example.copperadmin.testingadapters;

import android.content.Context;
import android.util.Log;

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

        ArrayList<String> reverseOrder = new ArrayList<String>();
        for(int i=pictureUrls.size()-1; i>0;i--){
            reverseOrder.add(pictureUrls.get(i));
        }

        reverseOrder.addAll(mPictureUrls);
        mPictureUrls = reverseOrder;

    }
}
