package com.ivoryworks.pgma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GestureFragment extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener{

    public static String TAG = GestureFragment.class.getSimpleName();
    private TextView mLogText;
    private GestureDetector mGestureDetector;

    public static GestureFragment newInstance() {
        return new GestureFragment();
    }

    public GestureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gesture, container, false);

        mLogText = (TextView) view.findViewById(R.id.touchLog);
        mLogText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mLogText.setText("Down\n" + mLogText.getText());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        mLogText.setText("ShowPress\n" + mLogText.getText());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mLogText.setText("SingleTapUp\n" + mLogText.getText());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        String formated = String.format("Scroll (dist:%f, %f)\n",distanceX, distanceY);
        mLogText.setText(formated + mLogText.getText());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mLogText.setText("LongPress\n" + mLogText.getText());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        String formated = String.format("Fling (velocity:%f, %f)\n", velocityX, velocityY);
        mLogText.setText(formated + mLogText.getText());
        return false;
    }
}
