package com.example.copperadmin.testingadapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GallaryFragment extends Fragment {

    GridView mGridView;
    private Gson gson;
    private String mJsonString;
    private ArrayList<String> mUrls;

    private static final String url = "https://api.instagram.com/v1/tags/selfie/media/recent?&count=100&access_token=1460323222.858d928.3b9e24a3be9e4278b9cd73e120044e0a";

    public GallaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String[].class, new SimpleDeserializer());

        gson = gsonBuilder.create();

        JsonObjectRequest request = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mJsonString = response.toString();
                String[] urls = gson.fromJson(response.toString(),String[].class);

                mUrls = new ArrayList<String>(Arrays.asList(urls));

                setupAdapter();

                for (String x :mUrls) {
                    Log.d("JSONString", x);
                }

            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSONString", error.toString());
            }
        });

        request.setTag(this);
        VolleyApplication.getInstance().getRequestQueue().add(request);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallary, container, false);
        mGridView = (GridView)v.findViewById(R.id.gridView);

        setupAdapter();

        return v;
    }
    void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;
        if (mUrls != null) {
            mGridView.setAdapter(new GalleryItemAdapter(mUrls));
        } else {
            mGridView.setAdapter(null);
        }
    }

    private class GalleryItemAdapter extends ArrayAdapter<String> {
        public GalleryItemAdapter(ArrayList<String> items) {
            super(getActivity(), 0, items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }

            String url = getItem(position);
            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.placeholder);

            Picasso.with(getActivity())
                    .load(url)
                    .into(imageView);
           return convertView;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VolleyApplication.getInstance().getRequestQueue().cancelAll(this);
    }

}
