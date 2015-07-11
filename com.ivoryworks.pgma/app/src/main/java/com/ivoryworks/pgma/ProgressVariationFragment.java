package com.ivoryworks.pgma;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProgressVariationFragment extends Fragment {

    public static String TAG = "ProgressVariationFragment";
    private static ProgressDialog mDialog;

    public static ProgressVariationFragment newInstance() {
        return new ProgressVariationFragment();
    }

    public ProgressVariationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_variation, container, false);

        Button styleSpinner = (Button) view.findViewById(R.id.btnStyleSpinner);
        styleSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new ProgressDialog(getActivity());
                mDialog.setTitle(R.string.caption_spinner);
                mDialog.setMessage(getString(R.string.msg_please_wait));
                mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mDialog.setCancelable(true);
                mDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDialog.dismiss();
                    }
                }).start();
            }
        });
        return view;
    }
}
