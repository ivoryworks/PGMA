package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TouchFragment extends Fragment implements View.OnTouchListener{

    public static String TAG = TouchFragment.class.getSimpleName();
    private TextView mLogText;

    public static TouchFragment newInstance() {
        return new TouchFragment();
    }

    public TouchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_touch, container, false);

        mLogText = (TextView) view.findViewById(R.id.touchLog);
        mLogText.setOnTouchListener(this);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String logLine = String.format("%s x:%f y:%f\n",
                Utils.actionToString(motionEvent.getAction()),
                motionEvent.getX(), motionEvent.getY());
        mLogText.setText(logLine + mLogText.getText());
        return true;
    }
}
