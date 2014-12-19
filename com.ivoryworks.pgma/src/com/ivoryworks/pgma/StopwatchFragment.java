package com.ivoryworks.pgma;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchFragment extends Fragment implements OnClickListener {

    private static StopwatchTask mStopwatchTask = null;
    private long mMSecCount = 0;
    private TextView mDigitText;
    private Button mBtnStartStop;
    private Button mBtnReset;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        
        mDigitText = (TextView) inflate.findViewById(R.id.digit);

        mBtnStartStop = (Button) inflate.findViewById(R.id.btn_start_stop);
        mBtnStartStop.setOnClickListener(this);
        mBtnReset = (Button) inflate.findViewById(R.id.btn_reset);
        mBtnReset.setOnClickListener(this);

        return inflate; 
    }

    @Override
    public void onResume() {
        mDigitText.setText(formatDigit(mMSecCount));
        super.onResume();
    }
    
    private String formatDigit(long msec) {
        long min = msec / (1000*60);
        long sec = (msec % (1000*60)) / 1000;
        long ms = msec % 1000;
        return String.format("%02d:%02d.%03d", min, sec, ms);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_start_stop:
            if (mBtnStartStop.getText().equals(getResources().getString(R.string.start))) {
                mBtnStartStop.setText(R.string.stop);
                mStopwatchTask = new StopwatchTask(System.currentTimeMillis() - mMSecCount);
                mStopwatchTask.execute();
            } else {
                mBtnStartStop.setText(R.string.start);
                mStopwatchTask.cancel(true);
            }
            break;
        case R.id.btn_reset:
            if (!mStopwatchTask.isCancelled()) {
                mStopwatchTask.cancel(true);
            }
            mMSecCount = 0;
            mDigitText.setText(formatDigit(mMSecCount));
            if (mBtnStartStop.getText().equals(getResources().getString(R.string.stop))) {
                mStopwatchTask = new StopwatchTask(System.currentTimeMillis() - mMSecCount);
                mStopwatchTask.execute();
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
