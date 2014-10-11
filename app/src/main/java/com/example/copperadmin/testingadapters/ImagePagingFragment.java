package com.example.copperadmin.testingadapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ImagePagingFragment extends Fragment {


    private ArrayList<String> mUrls;
    private int mPosition;
    private TextView mPositionTextView;
    private ImageView mPictureImageView;

    public ImagePagingFragment() {
        // Required empty public constructor
    }

    public static ImagePagingFragment newInstance (ArrayList<String> urls, int position){
        Bundle args = new Bundle();
        args.putSerializable("urls", urls);
        args.putInt("position",position);
        ImagePagingFragment fragment = new ImagePagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mUrls = (ArrayList<String>)(getArguments().getSerializable("urls"));
        mPosition = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_image_paging, container, false);
        mPositionTextView = (TextView)v.findViewById(R.id.imageNumber);
        mPositionTextView.setText("This is image "+mPosition);

        mPictureImageView = (ImageView)v.findViewById(R.id.imageContainer);
        Picasso.with(getActivity())
                .load(mUrls.get(mPosition))
                .into(mPictureImageView);



        return v;
    }


}
