package com.ivoryworks.pgma;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProgressVariationFragment extends Fragment {

    public static String TAG = ProgressVariationFragment.class.getSimpleName();
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

        Button styleHorizontal = (Button) view.findViewById(R.id.btnStyleHorizontal);
        styleHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new ProgressDialog(getActivity());
                mDialog.setTitle(R.string.caption_horizontal);
                mDialog.setMessage(getString(R.string.msg_please_wait));
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.setIndeterminate(false);
                mDialog.setMax(100);
                mDialog.incrementProgressBy(0);
                mDialog.setCancelable(true);
                mDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int max = 5;
                            for (int i= 0; i < max; i++) {
                                Thread.sleep(1000);
                                mDialog.setProgress((i+1)*(100/max));  // progress increment
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDialog.dismiss();
                    }
                }).start();
            }
        });

        Button styleHorizontal2nd = (Button) view.findViewById(R.id.btnStyleHorizontal2nd);
        styleHorizontal2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new ProgressDialog(getActivity());
                mDialog.setTitle(R.string.caption_horizontal);
                mDialog.setMessage(getString(R.string.msg_please_wait));
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.setIndeterminate(false);
                mDialog.setMax(100);
                mDialog.setSecondaryProgress(0);
                mDialog.incrementProgressBy(0);
                mDialog.setCancelable(true);
                mDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int max = 5;
                            for (int i= 0; i < max; i++) {
                                Thread.sleep(1000);
                                mDialog.setProgress((i+1)*(100/max));  // progress increment
                                mDialog.setSecondaryProgress(((i+2)*(100/max)));  // 2nd progress increment
                            }
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
