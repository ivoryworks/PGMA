package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    public static String TAG = GalleryFragment.class.getSimpleName();

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    public GalleryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_gallery, container, false);

        ArrayList<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);

        GridView gridView = (GridView) layoutView.findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

        return layoutView;
    }
}
