package com.ivoryworks.pgma;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickImageFragment extends Fragment {

    public static String TAG = PickImageFragment.class.getSimpleName();
    private final int REQ_CODE_ACTION_OPEN_DOCUMENT = 1;
    private final int REQ_CODE_ACTION_IMAGE_CAPTURE = 2;
    private final String PREF_NAME_IMAGE_PATH = TAG + "_ImagePath";
    private Uri mPhotoUri;
    private PreferencesManager mPreferencesManager;
    private ImageView mPreviewPhoto;
    private Toast mToast;
    private MenuItem mMenuShare;
    private View mParentView;
    private TextView mTextView;

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
        mToast = new Toast(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParentView = inflater.inflate(R.layout.fragment_pick_image, container, false);

        mTextView = (TextView) mParentView.findViewById(R.id.photoExif);
        mPreviewPhoto = (ImageView) mParentView.findViewById(R.id.previewPhoto);

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action)) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            setImage(uri);
        } else {
            String imagePath = mPreferencesManager.getString(PREF_NAME_IMAGE_PATH);
            if (imagePath != null && imagePath.isEmpty() != true) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
                    int orientation = Utils.getOrientationType(imagePath);
                    mPreviewPhoto.setImageBitmap(Utils.rotateBitmap(photoBitmap, orientation));
                }
            }
        }

        Toolbar toolbar = (Toolbar) mParentView.findViewById(R.id.tool_bar);

        MenuItem menuShare = toolbar.getMenu().add("share");
        menuShare.setIcon(R.drawable.ic_share_white_36dp);
        menuShare.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String imgPath = mPreferencesManager.getString(PREF_NAME_IMAGE_PATH);
                if (imgPath == null || imgPath.isEmpty()) {
                    Resources res = getResources();
                    String message = res.getString(R.string.msg_share_file_not_fond);
                    Utils.showSnackbar(mParentView, message);
                } else {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + imgPath));
                    startActivity(Intent.createChooser(share, "Share Image"));
                }
                return false;
            }
        });

        MenuItem menuCamera = toolbar.getMenu().add("camera");
        menuCamera.setIcon(R.drawable.ic_photo_camera_white_36dp);
        menuCamera.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuCamera.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mPhotoUri = createPhotoUri();
                Intent intent = Utils.createImageCaptureIntent(mPhotoUri);
                startActivityForResult(intent, REQ_CODE_ACTION_IMAGE_CAPTURE);
                return false;
            }
        });

        MenuItem menuGallery = toolbar.getMenu().add("gallery");
        menuGallery.setIcon(R.drawable.ic_photo_library_white_36dp);
        menuGallery.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuGallery.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = Utils.createGalleryPickIntent();
                startActivityForResult(intent, REQ_CODE_ACTION_OPEN_DOCUMENT);
                return false;
            }
        });

        return mParentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQ_CODE_ACTION_OPEN_DOCUMENT:
            if (resultCode == Activity.RESULT_OK) {
                setImage(data.getData());
                loadExif(data.getData());
            }
            break;
        case REQ_CODE_ACTION_IMAGE_CAPTURE:
            if (resultCode == Activity.RESULT_OK) {
                setImage(mPhotoUri);
                loadExif(data.getData());
            }
            break;
        }
    }

    private void setImage(Uri imageUri) {
        String imagePath = Utils.getPath(getActivity(), imageUri);

        // Save file path
        mPreferencesManager.setString(PREF_NAME_IMAGE_PATH, imagePath);

        Bitmap photoBitmap = BitmapFactory.decodeFile(imagePath);
        int orientation = Utils.getOrientationType(imagePath);
        mPreviewPhoto.setImageBitmap(Utils.rotateBitmap(photoBitmap, orientation));
    }

    private void loadExif(Uri imageUri) {
        try {
            ExifInterface exif = new ExifInterface(Utils.getPath(getActivity(), imageUri));
            String exifInfoText = "";
            exifInfoText += String.format("size:%d x %d\n",
                    exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1),
                    exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1));
            String[] tags = {ExifInterface.TAG_DATETIME, ExifInterface.TAG_ISO,
                    ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL, ExifInterface.TAG_ORIENTATION};
            for (String tag : tags) {
                exifInfoText += String.format("%s:%s\n", tag, exif.getAttribute(tag));
            }

            mTextView.setText(exifInfoText);
        } catch (IOException e) {
            e.printStackTrace();
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
