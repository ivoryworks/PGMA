package com.ivoryworks.pgma;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.IOException;

public class Utils {

    public static int getOrientationType(String path) {
        ExifInterface exifIf = null;
        try {
            exifIf = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exifIf.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
    }

    public static Bitmap rotateBitmap(Bitmap imageBitmap, int orientationType) {
        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();
        Matrix mat = new Matrix();
        switch(orientationType) {
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                mat.preScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                mat.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                mat.preScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                mat.postRotate(270);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                mat.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                mat.postRotate(90);
                mat.preScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                mat.postRotate(270);
                mat.preScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_NORMAL:
            case ExifInterface.ORIENTATION_UNDEFINED:
            default:
                break;
        }
        return Bitmap.createBitmap(imageBitmap, 0, 0, width, height, mat, true);
    }

    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) {
        if (isDocumentUri(context, uri)) {
            // Newest Document Uri( >= KitKat )
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Media store (and general)
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return DocumentsContract.isDocumentUri(context, uri);
        }
        return false;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String actionToString(int action) {
        switch(action) {
            case MotionEvent.ACTION_CANCEL:
                return "CANCEL";
            case MotionEvent.ACTION_DOWN:
                return "DOWN";
            case MotionEvent.ACTION_MOVE:
                return "MOVE";
            case MotionEvent.ACTION_OUTSIDE:
                return "OUTSIDE";
            case MotionEvent.ACTION_UP:
                return "UP";
            default:
                return "UNKNOWN";
        }
    }

    public static Toast showToast(Context context, Toast toast, int messageId, int lengthType) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, messageId, lengthType);
        toast.show();
        return toast;
    }
}
