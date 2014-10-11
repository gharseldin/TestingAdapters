package com.example.copperadmin.testingadapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GallaryFragment extends Fragment {

    GridView mGridView;
    private Gson gson;
    private PictureLab mPictureLab;

    private String url = "https://api.instagram.com/v1/tags/selfie/media/recent?&count=100&access_token=1460323222.858d928.3b9e24a3be9e4278b9cd73e120044e0a";

    public GallaryFragment() {
        // Required empty public constructor
    }

    public static GallaryFragment newInstance(String url){

        Bundle args = new Bundle();
        args.putString("URL", url);
        GallaryFragment fragment = new GallaryFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mPictureLab = PictureLab.get(getActivity());

        if(getArguments()!=null){
            url = getArguments().getString("URL");
        }

        updatePhotos(url);

        Intent i = new Intent(getActivity(), PollService.class);
        getActivity().startService(i);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallary, container, false);
        mGridView = (GridView)v.findViewById(R.id.gridView);
        setupAdapter();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("CLICKED!!!!!!!","Picture:"+i+" has been clicked");
                Intent intent = new Intent(getActivity(),ImagePagingActivity.class);
                intent.putExtra("urls",mPictureLab.getPictureUrls())
                        .putExtra("position", i);
                startActivity(intent);
            }
        });

        return v;
    }

    void updatePhotos(String url){

        /**
         * create the GsonBuilder and register the Deserializer class
         * Then create a Gson Object from that
         */
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ArrayList.class, new SimpleDeserializer());
        gson = gsonBuilder.create();

        JsonObjectRequest request = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                mPictureLab.addPictureUrls(gson.fromJson(response.toString(),ArrayList.class));
                Log.d("***","Size = "+mPictureLab.getPictureUrls().size());
                setupAdapter();

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

    void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;
        if (mPictureLab.getPictureUrls() != null) {
            mGridView.setAdapter(new GalleryItemAdapter(mPictureLab.getPictureUrls()));
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_gallary, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_search:
                getActivity().onSearchRequested();
                return true;
            case R.id.menu_item_clear:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VolleyApplication.getInstance().getRequestQueue().cancelAll(this);
    }
}
