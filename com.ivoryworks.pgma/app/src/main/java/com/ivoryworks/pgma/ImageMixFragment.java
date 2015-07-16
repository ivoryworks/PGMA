package com.ivoryworks.pgma;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageMixFragment extends Fragment {

    public static String TAG = "ImageMixFragment";
    private ImageView mImageMix;

    public static ImageMixFragment newInstance() {
        return new ImageMixFragment();
    }

    public ImageMixFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_mix, container, false);
        mImageMix = (ImageView) view.findViewById(R.id.imagemix);

        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream baseStream = assetManager.open(Constants.OCTOCAT_FILENAME);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(baseStream, null, options);
            int baseWidth = options.outWidth;
            int baseHeight = options.outHeight;

            int offsetX = baseWidth / 2;

            // Left side octocat
            InputStream leftSideStream = assetManager.open(Constants.OCTOCAT_FILENAME);
            BitmapRegionDecoder leftSideDecoder = BitmapRegionDecoder.newInstance(leftSideStream, true);
            Rect leftSideRect = new Rect(0, 0, offsetX, baseHeight);
            options.inJustDecodeBounds = false;
            Bitmap leftSideBitmap = leftSideDecoder.decodeRegion(leftSideRect, options);

            // Right side octcat(leftSideBitmap reversal)
            Bitmap rightSideBitmap =  Utils.rotateBitmap(leftSideBitmap, ExifInterface.ORIENTATION_FLIP_HORIZONTAL);

            // Draw Symmetry Octocat
            int mixWidth = leftSideBitmap.getWidth() + rightSideBitmap.getWidth();
            int mixHeight = baseHeight;
            Bitmap mixBitmap = Bitmap.createBitmap(mixWidth, mixHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mixBitmap);
            canvas.drawBitmap(leftSideBitmap, 0, 0, null);
            canvas.drawBitmap(rightSideBitmap, offsetX, 0, null);

            mImageMix.setImageBitmap(mixBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
}
