package com.ivoryworks.pgma;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ScaleTypeFragment extends Fragment {

    public static String TAG = ScaleTypeFragment.class.getSimpleName();

    public static ScaleTypeFragment newInstance() {
        return new ScaleTypeFragment();
    }

    public ScaleTypeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale_type, container, false);
        ImageView originImageView = (ImageView) view.findViewById(R.id.originImageView);
        Bitmap beetleBitmap = loadAssetImage(Constants.BEETLE_FILENAME, originImageView.getWidth());
        originImageView.setImageBitmap(beetleBitmap);

        return view;
    }

    private Bitmap loadAssetImage(String name, int reqWidth) {
        AssetManager manager = getResources().getAssets();
        InputStream inputStreamBase = null;
        try {
            inputStreamBase = manager.open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStreamBase != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamBase, null, options);

            options.inSampleSize = reqWidth / options.outWidth;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(inputStreamBase, null, options);
        }
        return null;
    }
}
