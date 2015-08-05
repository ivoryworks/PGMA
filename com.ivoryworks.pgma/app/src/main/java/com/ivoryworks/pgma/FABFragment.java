package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FABFragment extends Fragment implements View.OnClickListener {

    public static String TAG = FABFragment.class.getSimpleName();
    private final int REQ_CODE_ACTION_IMAGE_CAPTURE = 1;
    private final String PREF_NAME_IMAGE_PATH = TAG + "_ImagePath";
    private Uri mPhotoUri;
    private PreferencesManager mPreferencesManager;
    private ImageView mPreviewPhoto;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TakePhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FABFragment newInstance() {
        FABFragment fragment = new FABFragment();
        return fragment;
    }

    public FABFragment() {
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
        View layoutView = inflater.inflate(R.layout.fragment_fab, container, false);

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
        case R.id.btnFab:
            mPhotoUri = createPhotoUri();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, REQ_CODE_ACTION_IMAGE_CAPTURE);
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
                String imagePath = Utils.getPath(getActivity(), mPhotoUri);

                // Save file path
                mPreferencesManager.setString(PREF_NAME_IMAGE_PATH, imagePath);

                Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                int orientation = Utils.getOrientationType(imagePath);
                mPreviewPhoto.setImageBitmap(Utils.rotateBitmap(photoBitmap, orientation));
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
}
