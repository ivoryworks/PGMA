package com.ivoryworks.pgma;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class StopwatchFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static String TAG = "StopwatchFragment";
    private static StopwatchTask mStopwatchTask = null;
    private long mMSecCount;
    private TextView mDigitText;
    private ToggleButton mBtnStartStop;
    private Button mBtnReset;

    public static StopwatchFragment newInstance() {
        StopwatchFragment fragment = new StopwatchFragment();
        return fragment;
    }

    public StopwatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        mDigitText = (TextView) inflate.findViewById(R.id.digit);

        mBtnStartStop = (ToggleButton) inflate.findViewById(R.id.btnStartStop);
        mBtnStartStop.setOnCheckedChangeListener(this);
        mBtnReset = (Button) inflate.findViewById(R.id.btnReset);
        mBtnReset.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDigitText.setText(formatDigit(mMSecCount));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        if (mStopwatchTask != null && mStopwatchTask.isCancelled() != true) {
            mStopwatchTask.cancel(true);
        }
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnReset:
            if (mStopwatchTask != null && mStopwatchTask.isCancelled() != true) {
                mStopwatchTask.cancel(true);
            }
            mMSecCount = 0;
            mDigitText.setText(formatDigit(mMSecCount));
            if (mBtnStartStop.isChecked()) {
                mStopwatchTask = new StopwatchTask(System.currentTimeMillis() - mMSecCount);
                mStopwatchTask.execute();
            }
            break;
        }
    }

    private static String formatDigit(long msec) {
        long hour = msec / (1000 * 60 * 60);
        long min = msec / (1000 * 60);
        long sec = (msec % (1000 * 60)) / 1000;
        long ms = msec % 1000;
        return String.format("%02d:%02d:%02d.%03d", hour, min, sec, ms);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
        case R.id.btnStartStop:
            if (isChecked) {
                if (mStopwatchTask != null && mStopwatchTask.isCancelled() != true) {
                    mStopwatchTask.cancel(true);
                }
                mStopwatchTask = new StopwatchTask(System.currentTimeMillis() - mMSecCount);
                mStopwatchTask.execute();
            } else {
                mStopwatchTask.cancel(true);
            }
            break;
        }
    }

    public class StopwatchTask extends AsyncTask<Long, Long, Long> {
        private long mStartMSec;
        public StopwatchTask(long msec) {
            mStartMSec = msec;
        }
        /**
         * work in UI thread
         */
        @Override
        protected void onProgressUpdate(Long... values) {
            mDigitText.setText(formatDigit(values[0]));
        }

        @Override
        protected void onCancelled() {
            setMSecCount();
            super.onCancelled();
        }

        /**
         * work in Worker thread
         */
        @Override
        protected Long doInBackground(Long... arg0) {
            try {
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    publishProgress(System.currentTimeMillis() - mStartMSec);
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void setMSecCount() {
            mMSecCount = System.currentTimeMillis() - mStartMSec;
        }
    }
}
