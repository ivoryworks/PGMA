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

import java.io.IOException;
import java.io.InputStream;

public class ImageCropFragment extends Fragment {

    private final static String IMAGEFILENAME = "octocat.png";
    private final int CROP_WIDTH = 200;
    private final int CROP_HEIGHT = 200;

    public static String TAG = ImageCropFragment.class.getSimpleName();
    private ImageView mImageCrop;

    public static ImageCropFragment newInstance() {
        return new ImageCropFragment();
    }

    public ImageCropFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_crop, container, false);
        mImageCrop = (ImageView) view.findViewById(R.id.imagecrop);

        BitmapFactory.Options options = new BitmapFactory.Options();
        AssetManager manager = getResources().getAssets();
        try {
            InputStream inputStreamBase = manager.open(IMAGEFILENAME);
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamBase, null, options);
            int baseWidth = options.outWidth;
            int baseHeight = options.outHeight;

            InputStream inputStreamCrop = manager.open(IMAGEFILENAME);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStreamCrop, true);
            int offsetX = baseWidth / 4;
            int offsetY = baseHeight / 4;
            Rect rect = new Rect(offsetX, offsetY, offsetX + CROP_WIDTH, offsetY + CROP_HEIGHT);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = decoder.decodeRegion(rect, options);
            mImageCrop.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }
}
