package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
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

public class PickImage2Fragment extends Fragment implements View.OnClickListener {

    public static String TAG = PickImage2Fragment.class.getSimpleName();
    private final int REQ_CODE_ACTION_IMAGE_CAPTURE = 1;
    private final String PREF_NAME_IMAGE_PATH = TAG + "_ImagePath";
    private Uri mPhotoUri;
    private PreferencesManager mPreferencesManager;
    private ImageView mPreviewPhoto;

    public static PickImage2Fragment newInstance() {
        PickImage2Fragment fragment = new PickImage2Fragment();
        return fragment;
    }

    public PickImage2Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferencesManager = PreferencesManager.newInstance(getActivity());
        mPreferencesManager.remove(PREF_NAME_IMAGE_PATH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_pick_image2, container, false);

        layoutView.findViewById(R.id.btnFab).setOnClickListener(this);
        mPreviewPhoto = (ImageView) layoutView.findViewById(R.id.previewPhoto);

        String imagePath = mPreferencesManager.getString(PREF_NAME_IMAGE_PATH);
        if (imagePath != null && imagePath.isEmpty() != true) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                int orientation = Utils.getOrientationType(imagePath);
                mPreviewPhoto.setImageBitmap(Utils.rotateBitmap(photoBitmap, orientation));
            }
        }

        return layoutView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnFab:
            Intent intent = Utils.createGalleryPickIntent();  // to Gallery

            File pathExternalPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String filename = System.currentTimeMillis() + ".jpg";
            File capturedFile = new File(pathExternalPublicDir, filename);
            mPhotoUri = Uri.fromFile(capturedFile);
            Intent camIntent = Utils.createImageCaptureIntent(mPhotoUri);

            Intent chooserIntent = Intent.createChooser(intent, "Pick Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{camIntent});

            startActivityForResult(chooserIntent, REQ_CODE_ACTION_IMAGE_CAPTURE);
            break;
        default:
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQ_CODE_ACTION_IMAGE_CAPTURE:
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = (data == null) ? mPhotoUri : data.getData();
                if (resultUri == null) {
                    return;
                }
                String imagePath = Utils.getPath(getActivity(), resultUri);

                MediaScannerConnection.scanFile(getActivity(), new String[]{resultUri.getPath()},
                        new String[]{"image/jpeg"}, null);

                // Save file path
                mPreferencesManager.setString(PREF_NAME_IMAGE_PATH, imagePath);

                Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                int orientation = Utils.getOrientationType(imagePath);
                mPreviewPhoto.setImageBitmap(Utils.rotateBitmap(photoBitmap, orientation));
            }
            break;
        }
    }
}
