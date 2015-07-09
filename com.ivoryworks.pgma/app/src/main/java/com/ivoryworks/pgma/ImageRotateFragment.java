package com.ivoryworks.pgma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.ivoryworks.pgma.Utils.setBitmap2ImageView;

public class ImageRotateFragment extends Fragment {

    public static String TAG = "ImageRotateFragment";

    public static ImageRotateFragment newInstance() {
        return new ImageRotateFragment();
    }

    public ImageRotateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_rotate, container, false);

        Bitmap bitmapOctocat = BitmapFactory.decodeResource(getResources(), R.drawable.octocat);

        ImageView celNomal = (ImageView) view.findViewById(R.id.gridCel1);
        celNomal.setImageBitmap(bitmapOctocat);

        ImageView celRotate270 = (ImageView) view.findViewById(R.id.gridCel3);
        setBitmap2ImageView(celRotate270, bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_270);

        ImageView celRotate90 = (ImageView) view.findViewById(R.id.gridCel5);
        setBitmap2ImageView(celRotate90, bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_90);

        ImageView celRotate180 = (ImageView) view.findViewById(R.id.gridCel7);
        setBitmap2ImageView(celRotate180, bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_180);

        return view;
    }
}
