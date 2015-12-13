package com.ivoryworks.pgma;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class ImageDownloadTask extends AsyncTask<Void, Void, Boolean>
{
    private Context mContext;
    private String TAG="ImageDownloadTask";
    private ProgressDialog mProgressDialog;
    private Uri mInUri;
    private File mOutFile;

    public ImageDownloadTask(Uri inUri, File outFile, Context context) {
        this.mContext = context;
        this.mInUri = inUri;
        this.mOutFile = outFile;
        this.mProgressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String message = "Saving file...";
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        if (!mOutFile.exists()) {
            if (!mOutFile.mkdirs()) {
                Log.v(TAG,"Directory was not created");
                return null;
            }
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = mContext.getContentResolver().openInputStream(mInUri);
            outputStream =  new FileOutputStream(mOutFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            try {
                while ((read = inputStream.read(bytes)) != -1) {
                    try {
                        outputStream.write(bytes, 0, read);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();
        if(result == true) {
            String[] paths = {mOutFile.getPath()};
            String[] mimeTypes = {getMimeType(mOutFile)};
            MediaScannerConnection.scanFile(mContext.getApplicationContext(),
                                            paths,
                                            mimeTypes,
                                            null);
        } else {
            if (mOutFile.exists()) {
                mOutFile.delete();
            }
        }
    }

    private String getMimeType(File file) {
        String fileName = file.getName();
        int ch = fileName.lastIndexOf('.');
        String ext = (ch>=0) ? fileName.substring(ch + 1) : "jpg";

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
    }
}
