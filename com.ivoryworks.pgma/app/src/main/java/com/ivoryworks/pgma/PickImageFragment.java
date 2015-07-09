package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

public class PickImageFragment extends Fragment implements View.OnClickListener {

    public static String TAG = "PickImageFragment";
    private final int REQ_CODE_ACTION_OPEN_DOCUMENT = 1;
    private final String PREF_NAME_IMAGE_PATH = TAG + "_ImagePath";
    private PreferencesManager mPreferencesManager;
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
        View layoutView = inflater.inflate(R.layout.fragment_pick_image, container, false);

        layoutView.findViewById(R.id.btnGallery).setOnClickListener(this);
        mPreviewPhoto = (ImageView) layoutView.findViewById(R.id.previewPhoto);

        String imagePath = mPreferencesManager.getString(PREF_NAME_IMAGE_PATH);
        if (imagePath != null && imagePath.isEmpty() != true) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                int orientation = Utils.getOrientationType(imagePath);
                Utils.setBitmap2ImageView(mPreviewPhoto, photoBitmap, orientation);
            }
        }

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
                String imagePath = Utils.getPath(getActivity(), imageUri);

                // Save file path
                mPreferencesManager.setString(PREF_NAME_IMAGE_PATH, imagePath);

                Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                int orientation = Utils.getOrientationType(imagePath);
                Utils.setBitmap2ImageView(mPreviewPhoto, photoBitmap, orientation);
            }
            break;
        }
    }
}
