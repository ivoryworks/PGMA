package com.ivoryworks.pgma;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class FontFragment extends Fragment {

    public static String TAG = FontFragment.class.getSimpleName();

    public static FontFragment newInstance() {
        return new FontFragment();
    }

    public FontFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_font, container, false);

        Typeface tfRobotoBlack = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Black.ttf");
        TextView textRobotoBlack = (TextView) view.findViewById(R.id.Roboto_Black);
        textRobotoBlack.setTypeface(tfRobotoBlack);

        return view;
    }
}
