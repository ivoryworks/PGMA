package com.ivoryworks.pgma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class PinchGestureDetectView extends View {
    private String TAG = PinchGestureDetectView.class.getSimpleName();
    private ScaleGestureDetector mScaleGestureDetector;
    private Drawable mImage;
    private float mScaleFactor = 1.0f;

    private ScaleGestureDetector.SimpleOnScaleGestureListener mSimpleListener
            = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin : " + detector.getScaleFactor());
            invalidate();
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd : "+ detector.getScaleFactor());
            mScaleFactor *= detector.getScaleFactor();
            invalidate();
            super.onScaleEnd(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "onScale : "+ detector.getScaleFactor());
            mScaleFactor *= detector.getScaleFactor();
            invalidate();
            return true;
        };
    };

    public PinchGestureDetectView(Context context) {
        super(context);
        mImage = context.getResources().getDrawable(R.drawable.octocat_l);
        mImage.setBounds(0, 0, mImage.getIntrinsicWidth(), mImage.getIntrinsicHeight());
        mScaleGestureDetector = new ScaleGestureDetector(context, mSimpleListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        mImage.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
