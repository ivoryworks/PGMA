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

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        float originWidth = options.outWidth;
        float originHeith = options.outHeight;
        float gridCelWidth = getResources().getDimensionPixelSize(R.dimen.image_rotate_cel_width);
        float gridCelHeight = getResources().getDimensionPixelSize(R.dimen.image_rotate_cel_height);

        BitmapFactory.decodeResource(getResources(), R.drawable.octocat, options);
        if (originWidth >= originHeith) {
            options.inSampleSize = Math.round(originHeith / gridCelHeight);
        } else {
            options.inSampleSize = Math.round(originWidth / gridCelWidth);
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmapOctocat = BitmapFactory.decodeResource(getResources(), R.drawable.octocat, options);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel0),
                bitmapOctocat, ExifInterface.ORIENTATION_FLIP_VERTICAL);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel1),
                bitmapOctocat, ExifInterface.ORIENTATION_NORMAL);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel2),
                bitmapOctocat, ExifInterface.ORIENTATION_FLIP_HORIZONTAL);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel3),
                bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_270);

        // R.id.gridCel4 is TextView

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel5),
                bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_90);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel6),
                bitmapOctocat, ExifInterface.ORIENTATION_TRANSPOSE);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel7),
                bitmapOctocat, ExifInterface.ORIENTATION_ROTATE_180);

        setBitmap2ImageView((ImageView) view.findViewById(R.id.gridCel8),
                bitmapOctocat, ExifInterface.ORIENTATION_TRANSVERSE);

        return view;
    }
}
