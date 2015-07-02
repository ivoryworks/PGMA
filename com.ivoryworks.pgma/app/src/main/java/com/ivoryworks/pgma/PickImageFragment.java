package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickImageFragment extends Fragment implements View.OnClickListener {

    private final int REQ_CODE_ACTION_OPEN_DOCUMENT = 1;
    private ImageView mPreviewPhoto;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TakePhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickImageFragment newInstance() {
        PickImageFragment fragment = new PickImageFragment();
        return fragment;
    }

    public PickImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_pick_image, container, false);

        layoutView.findViewById(R.id.btnGallery).setOnClickListener(this);
        mPreviewPhoto = (ImageView) layoutView.findViewById(R.id.previewPhoto);

        return layoutView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnGallery:
            Intent intent;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                // for Oldies
                intent = new Intent(Intent.ACTION_GET_CONTENT);
            } else {
                // for KitKat
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            }
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("image/*");
            startActivityForResult(intent, REQ_CODE_ACTION_OPEN_DOCUMENT);
            break;
        default:
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQ_CODE_ACTION_OPEN_DOCUMENT:
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                int orientation = getOrientationType(imageUri);
                setMatrix(mPreviewPhoto, imageUri, orientation);
            }
            break;
        }
    }

    private Uri createPhotoUri() {
        // DCIM directory
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        String timeStamp = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss").format(new Date());
        File mediaFile = new File(dcim.getPath() + File.separator + timeStamp + ".jpg");
        return Uri.fromFile(mediaFile);
    }

    private int getOrientationType(Uri uri) {
        ExifInterface exifIf = null;
        try {
            exifIf = new ExifInterface(uri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exifIf.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
    }

    private void setMatrix(ImageView imageView, Uri photoUri, int orientationType) {
        Context context = getActivity().getBaseContext();
        try {
            Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
            int width = photoBitmap.getWidth();
            int height = photoBitmap.getHeight();
            Bitmap rotateBitmap;
            Matrix mat = new Matrix();
            switch(orientationType) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotateBitmap = photoBitmap;
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    rotateBitmap = photoBitmap;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mat.postRotate(180);
                    rotateBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, width, height, mat, true);
                    break;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    rotateBitmap = photoBitmap;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    mat.postRotate(270);
                    rotateBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, width, height, mat, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mat.postRotate(90);
                    rotateBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, width, height, mat, true);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    rotateBitmap = photoBitmap;
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    rotateBitmap = photoBitmap;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                default:
                    rotateBitmap = photoBitmap;
                    break;
            }
            imageView.setImageBitmap(rotateBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
