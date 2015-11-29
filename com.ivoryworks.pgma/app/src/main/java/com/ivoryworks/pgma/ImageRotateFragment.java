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

public class ImageRotateFragment extends Fragment {

    public static String TAG = ImageRotateFragment.class.getSimpleName();

    public static ImageRotateFragment newInstance() {
        return new ImageRotateFragment();
    }

    public ImageRotateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_rotate, container, false);

        setRotateImage(view, R.id.gridCel0, ExifInterface.ORIENTATION_FLIP_VERTICAL);
        setRotateImage(view, R.id.gridCel1, ExifInterface.ORIENTATION_NORMAL);
        setRotateImage(view, R.id.gridCel2, ExifInterface.ORIENTATION_FLIP_HORIZONTAL);
        setRotateImage(view, R.id.gridCel3, ExifInterface.ORIENTATION_ROTATE_270);

        // R.id.gridCel4 is TextView

        setRotateImage(view, R.id.gridCel5, ExifInterface.ORIENTATION_ROTATE_90);
        setRotateImage(view, R.id.gridCel6, ExifInterface.ORIENTATION_TRANSPOSE);
        setRotateImage(view, R.id.gridCel7, ExifInterface.ORIENTATION_ROTATE_180);
        setRotateImage(view, R.id.gridCel8, ExifInterface.ORIENTATION_TRANSVERSE);

        return view;
    }

    private void setRotateImage(View view, int resId, int type) {
        Bitmap bitmapOctocat = BitmapFactory.decodeResource(getResources(), R.drawable.octocat_l);

        ImageView imageView = (ImageView) view.findViewById(resId);
        if (imageView != null) {
            imageView.setImageBitmap(Utils.rotateBitmap(bitmapOctocat, type));
        }
    }
}
